package com.informatorio.proyectoapi.repository;

import com.informatorio.proyectoapi.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {


    @Query(value = "SELECT * FROM article WHERE title LIKE %:palabra% OR description LIKE %:palabra%",nativeQuery = true)
    List<Article> devolverPorPalabraEnTituloYDescription(String palabra);
}
