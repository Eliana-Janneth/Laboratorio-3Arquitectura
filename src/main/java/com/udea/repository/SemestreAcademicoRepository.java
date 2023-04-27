package com.udea.repository;

import com.udea.domain.SemestreAcademico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SemestreAcademico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SemestreAcademicoRepository extends JpaRepository<SemestreAcademico, Long> {}
