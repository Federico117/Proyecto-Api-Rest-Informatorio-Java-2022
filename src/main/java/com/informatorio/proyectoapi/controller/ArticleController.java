package com.informatorio.proyectoapi.controller;

import com.informatorio.proyectoapi.converter.ArticleConverter;
import com.informatorio.proyectoapi.domain.Article;
import com.informatorio.proyectoapi.domain.Author;
import com.informatorio.proyectoapi.dto.ArticleDto;
import com.informatorio.proyectoapi.repository.ArticleRepository;
import com.informatorio.proyectoapi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ArticleController {

    private ArticleRepository articleRepository;
    private AuthorRepository authorRepository;

    private ArticleConverter articleConverter;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, AuthorRepository authorRepository, ArticleConverter articleConverter) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.articleConverter = articleConverter;
    }

    //ESTE FUNCIONA CON LOS ARTICCLES QUE NO TIENEN ASIGNADO UN AUTHOR PORQUE SERA?
    @GetMapping(value = "/article")
    public List<ArticleDto> getArticle(){
        return articleRepository.findAll().stream().map(x->articleConverter.toDto(x)).collect(Collectors.toList());
    }

    /*@GetMapping(value = "/article")
    public List<Article> getArticle(){
        return articleRepository.findAll();
    }*/
    @GetMapping(value = "/article/{id_article}")
    public ArticleDto getArticlePorId(@PathVariable Long id_article){
        return articleConverter.toDto(articleRepository.findById(id_article).orElse(null));
    }

    @PostMapping(value = "/article")
    public Article createArticle(@RequestBody Article article){
        Article articleToSave = article;

        articleToSave.setPublishedAt(LocalDate.now());
        return articleRepository.save(articleToSave);
    }

    @PostMapping(value = "/article/{idArticle}/author")
    public Article addAuthorToArticle(@PathVariable Long idArticle, @RequestBody Long idAuthor){
        Author author =authorRepository.findById(idAuthor).orElse(null);//se debe agregar este autor que se encontro al articulo
        Article article = articleRepository.findById(idArticle).get();
        article.setAuthor(author);
        return articleRepository.save(article);
    }
}