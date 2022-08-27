package com.informatorio.proyectoapi.controller;

import com.informatorio.proyectoapi.converter.AuthorConverter;
import com.informatorio.proyectoapi.domain.Article;
import com.informatorio.proyectoapi.domain.Author;
import com.informatorio.proyectoapi.dto.AuthorDto;
import com.informatorio.proyectoapi.repository.ArticleRepository;
import com.informatorio.proyectoapi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping(value = "/author")
    public List<AuthorDto> getAuthor(){
        return authorConverter.toDtos(authorRepository.findAll());
    }

    @PostMapping(value = "/author")
    public Author createAutor(@RequestBody Author author){
        if(author.getCreationDate() == null){
            author.setCreationDate(LocalDate.now());
        }
        return authorRepository.save(author);
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
}
