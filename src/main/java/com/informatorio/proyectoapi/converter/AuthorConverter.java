package com.informatorio.proyectoapi.converter;

import com.informatorio.proyectoapi.domain.Author;
import com.informatorio.proyectoapi.dto.AuthorDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorConverter {

    //ULTIMA MODIFICACION DESDE QUE ANDUBO TODOs
    public List<AuthorDto> toDtos(List<Author> authors){
        return authors.stream()
                .map(actor -> toDto(actor)).collect(Collectors.toList());
    }
    public AuthorDto toDto(Author author){
        return new AuthorDto(author.getId(),
                author.getName(),
                author.getLastName(),
                author.getFullName(),
                author.getCreationDate());
    }

    public Author toEntity(AuthorDto authorDto){
        return new Author(authorDto.getId(), authorDto.getName(), authorDto.getLastName(), authorDto.getFullName(), authorDto.getCreationDate());
    }
}
