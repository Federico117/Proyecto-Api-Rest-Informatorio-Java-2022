package com.informatorio.proyectoapi.converter;

import com.informatorio.proyectoapi.domain.Article;
import com.informatorio.proyectoapi.dto.ArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleConverter {

    private AuthorConverter authorConverter;
    private SourceConverter sourceConverter;

    @Autowired
    public ArticleConverter(AuthorConverter authorConverter, SourceConverter sourceConverter) {
        this.authorConverter = authorConverter;
        this.sourceConverter = sourceConverter;
    }

    /*public List<ArticleDto> toDtos(List<Article> articles){

    }*/
    /*public ArticleDto toDto(Article article){
        return new ArticleDto(article.getId(),
                article.getTitle(),
                article.getDescription(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getPublishedAt(),
                article.getContent(), authorConverter.toDto(article.getAuthor()));
        //al llamar al ultimo metodo para convertir la entidad que trata de convertir no tiene author asignado por eso da null pointer entonces que hacemos?
    }*/
    public ArticleDto toDto(Article article){
        ArticleDto articulo = new ArticleDto(article.getId(),
                article.getTitle(),
                article.getDescription(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getPublishedAt(),
                article.getContent());
        if(article.getAuthor() != null){
            articulo.setAuthorDto(authorConverter.toDto(article.getAuthor()));
        }
        if(article.getSource() != null){
            articulo.setSourceDto(sourceConverter.toDto(article.getSource()));
        }
        return articulo;
        //al llamar al ultimo metodo para convertir la entidad que trata de convertir no tiene author asignado por eso da null pointer entonces que hacemos?
    }
}
