package com.informatorio.proyectoapi.controller;

import com.informatorio.proyectoapi.converter.AuthorConverter;
import com.informatorio.proyectoapi.domain.Article;
import com.informatorio.proyectoapi.domain.Author;
import com.informatorio.proyectoapi.domain.Source;
import com.informatorio.proyectoapi.dto.AuthorDto;
import com.informatorio.proyectoapi.dto.SourceDto;
import com.informatorio.proyectoapi.repository.ArticleRepository;
import com.informatorio.proyectoapi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private AuthorRepository authorRepository;

    private ArticleRepository articleRepository;
    private AuthorConverter authorConverter;

    @Autowired
    public AuthorController(AuthorRepository authorRepository, ArticleRepository articleRepository, AuthorConverter authorConverter) {
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
        this.authorConverter = authorConverter;
    }

    /*@GetMapping(value = "/author/{id_author}")
    public AuthorDto getAuthor(@PathVariable Long id_author){
        return authorConverter.toDto(authorRepository.findById(id_author).orElse(null));
    }*/

    //este tenia problemas para devolver los entitis asi que lo cambie para que devuelva el string del title del articulo del author q se le pasaba o los ids delos arts
    @GetMapping(value = "/author/{id_author}")
    public List<Long> getAuthor(@PathVariable Long id_author){
        return authorRepository.findById(id_author).orElse(null).getArticles().stream().map(x -> x.getId()).collect(Collectors.toList());
    }

    /*@GetMapping(value = "/author")
    public List<AuthorDto> getAuthor(@RequestParam(required = false) String fecha){
        if(fecha != null){
            LocalDate date = LocalDate.parse(fecha);
            return authorConverter.toDtos(authorRepository.devolverporfecha(date));
        }
        return authorConverter.toDtos(authorRepository.findAll());
    }*/
    @GetMapping(value = "/author")
    public List<AuthorDto> getAllAuthors(@RequestParam(required = false) String fecha,
                                         @RequestParam(required = false) String palabra){
        if(fecha != null && palabra != null){
            LocalDate date = LocalDate.parse(fecha);
            List<Author> dateFilteredList = authorRepository.devolverporfecha(date);
            return dateFilteredList.stream().filter(x -> x.getFullName().toLowerCase().contains(palabra.toLowerCase())).map(x->authorConverter.toDto(x)).collect(Collectors.toList());
        }else if(fecha != null){
            LocalDate date = LocalDate.parse(fecha);
            return authorConverter.toDtos(authorRepository.devolverporfecha(date));
        } else if (palabra != null) {
            return authorRepository.findAll().stream().filter(x -> x.getFullName().toLowerCase().contains(palabra.toLowerCase())).map(x->authorConverter.toDto(x)).collect(Collectors.toList());
        }
        return authorConverter.toDtos(authorRepository.findAll());
    }

    @PostMapping(value = "/author")
    public AuthorDto createAutor(@RequestBody AuthorDto author){
        /*este metodo tambien actualiza los valores de un entity existente
        cuando le pasamos un id, no deberia porque para eso esta el putmapping
        para evitar que pase eso setteo el id que recibe a null
         */
        if(author.getId() != null)author.setId(null);
        Author authorToSave = authorConverter.toEntity(author);
        if(author.getCreationDate() == null){
            authorToSave.setCreationDate(LocalDate.now());
        }
        return authorConverter.toDto(authorRepository.save(authorToSave));
    }

    //esto no va
    @PostMapping(value = "/author/{id_author}/article")
    public AuthorDto addArticleToAuthor(@PathVariable Long id_author, @RequestBody List<Long> articleIds){
        Author author = authorRepository.findById(id_author).orElse(null);
        List<Article> articles = articleIds.stream()
                .map(id -> articleRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        for(Article article: articles){
            author.addArticle(article);
        }
        author = authorRepository.save(author);
        return authorConverter.toDto(author);

    }

    @DeleteMapping(value = "/author/{id_author}")
    public void deleteAuthorById(@PathVariable Long id_author){
        authorRepository.deleteById(id_author);
    }

    /*pense que este metodo iba funcionar de manera errornea si no le pasabos un id o le pasabamos uno no existente
     iba a crear un nuevo entity pero no porque lo que hace es buscar uno existente segun el
     id que recibe de la request y modificar sus campos si los que recibe no son nulos
     */
    @PutMapping(value = "/author")
    public AuthorDto modifyAuthor(@RequestBody Author source){
        Author authorToModify = authorRepository.findById(source.getId()).orElse(null);
        if(source.getName() != null){
            authorToModify.setName(source.getName());
        }
        if(source.getLastName() != null){
            authorToModify.setLastName(source.getLastName());
        }
        if(source.getFullName() != null){
            authorToModify.setFullName(source.getFullName());
        }
        if(source.getCreationDate() != null){
            authorToModify.setCreationDate(source.getCreationDate());
        }
        return authorConverter.toDto(authorRepository.save(authorToModify));
    }
}
