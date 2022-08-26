package com.informatorio.proyectoapi.repository;

import com.informatorio.proyectoapi.domain.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {
}
