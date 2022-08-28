package com.informatorio.proyectoapi.repository;

import com.informatorio.proyectoapi.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {


    @Query(value = "SELECT * FROM author WHERE creation_date >= :fecha", nativeQuery = true)
    List<Author> devolverporfecha(LocalDate fecha);
}
