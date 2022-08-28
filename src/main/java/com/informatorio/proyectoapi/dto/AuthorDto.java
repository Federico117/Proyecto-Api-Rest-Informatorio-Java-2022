package com.informatorio.proyectoapi.dto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class AuthorDto {
    private Long id;
    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotBlank
    private String fullName;
    private LocalDate creationDate;

    public AuthorDto(Long id, String name, String lastName, String fullName, LocalDate creationDate) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.fullName = fullName;
        this.creationDate = creationDate;
    }

    public AuthorDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }


}
