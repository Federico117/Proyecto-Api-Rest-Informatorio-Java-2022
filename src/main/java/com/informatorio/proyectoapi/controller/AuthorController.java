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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
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

    @GetMapping(value = "/author/{id_author}")
    public ResponseEntity<?> getAuthor(@PathVariable Long id_author){
        if(authorRepository.findById(id_author).isPresent()){
            AuthorDto authorDto = authorConverter.toDto(authorRepository.findById(id_author).get());
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //aca me falta tratar si la request no pasa la validacion de la palabra de mayor a 3 letras
    @GetMapping(value = "/author")
    public List<AuthorDto> getAllAuthors(@RequestParam(required = false) String fecha,@Valid @Size(min = 4)
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
    public ResponseEntity<AuthorDto> createAutor(@RequestBody @Valid AuthorDto author){
        if(author.getId() != null)author.setId(null);
        Author authorToSave = authorConverter.toEntity(author);
        if(author.getCreationDate() == null){
            authorToSave.setCreationDate(LocalDate.now());
        }
        return new ResponseEntity<>(authorConverter.toDto(authorRepository.save(authorToSave)), HttpStatus.CREATED);
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
