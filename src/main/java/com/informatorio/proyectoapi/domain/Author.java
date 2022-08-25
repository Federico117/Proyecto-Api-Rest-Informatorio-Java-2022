package com.informatorio.proyectoapi.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String fullName;
    private LocalDate creationDate;

    //la clase no cuenta con getters y setters de este atributo y no se si son necesarios
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles= new ArrayList<>();

    public Author(Long id, String name, String lastName, String fullName, LocalDate creationDate) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.fullName = fullName;
        this.creationDate = creationDate;
    }

    public Author() {
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

    public List<Article> getArticles() {
        return articles;
    }

    public void addArticle(Article article) {
        articles.add(article);
        article.setAuthor(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(name, author.name) && Objects.equals(lastName, author.lastName) && Objects.equals(fullName, author.fullName) && Objects.equals(creationDate, author.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, fullName, creationDate);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
