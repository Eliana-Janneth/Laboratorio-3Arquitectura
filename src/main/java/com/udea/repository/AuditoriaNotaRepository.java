package com.udea.repository;

import com.udea.domain.AuditoriaNota;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AuditoriaNota entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditoriaNotaRepository extends JpaRepository<AuditoriaNota, Long> {}
