package com.udea.repository;

import com.udea.domain.GrupoEstudiante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GrupoEstudiante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoEstudianteRepository extends JpaRepository<GrupoEstudiante, Long> {}
