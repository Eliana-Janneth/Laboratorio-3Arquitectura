package com.udea.web.rest;

import com.udea.domain.AuditoriaNota;
import com.udea.repository.AuditoriaNotaRepository;
import com.udea.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.udea.domain.AuditoriaNota}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AuditoriaNotaResource {

    private final Logger log = LoggerFactory.getLogger(AuditoriaNotaResource.class);

    private static final String ENTITY_NAME = "auditoriaNota";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuditoriaNotaRepository auditoriaNotaRepository;

    public AuditoriaNotaResource(AuditoriaNotaRepository auditoriaNotaRepository) {
        this.auditoriaNotaRepository = auditoriaNotaRepository;
    }

    /**
     * {@code POST  /auditoria-notas} : Create a new auditoriaNota.
     *
     * @param auditoriaNota the auditoriaNota to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditoriaNota, or with status {@code 400 (Bad Request)} if the auditoriaNota has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/auditoria-notas")
    public ResponseEntity<AuditoriaNota> createAuditoriaNota(@Valid @RequestBody AuditoriaNota auditoriaNota) throws URISyntaxException {
        log.debug("REST request to save AuditoriaNota : {}", auditoriaNota);
        if (auditoriaNota.getId() != null) {
            throw new BadRequestAlertException("A new auditoriaNota cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuditoriaNota result = auditoriaNotaRepository.save(auditoriaNota);
        return ResponseEntity
            .created(new URI("/api/auditoria-notas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /auditoria-notas/:id} : Updates an existing auditoriaNota.
     *
     * @param id the id of the auditoriaNota to save.
     * @param auditoriaNota the auditoriaNota to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditoriaNota,
     * or with status {@code 400 (Bad Request)} if the auditoriaNota is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auditoriaNota couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/auditoria-notas/{id}")
    public ResponseEntity<AuditoriaNota> updateAuditoriaNota(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AuditoriaNota auditoriaNota
    ) throws URISyntaxException {
        log.debug("REST request to update AuditoriaNota : {}, {}", id, auditoriaNota);
        if (auditoriaNota.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auditoriaNota.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auditoriaNotaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuditoriaNota result = auditoriaNotaRepository.save(auditoriaNota);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditoriaNota.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /auditoria-notas/:id} : Partial updates given fields of an existing auditoriaNota, field will ignore if it is null
     *
     * @param id the id of the auditoriaNota to save.
     * @param auditoriaNota the auditoriaNota to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditoriaNota,
     * or with status {@code 400 (Bad Request)} if the auditoriaNota is not valid,
     * or with status {@code 404 (Not Found)} if the auditoriaNota is not found,
     * or with status {@code 500 (Internal Server Error)} if the auditoriaNota couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/auditoria-notas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AuditoriaNota> partialUpdateAuditoriaNota(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AuditoriaNota auditoriaNota
    ) throws URISyntaxException {
        log.debug("REST request to partial update AuditoriaNota partially : {}, {}", id, auditoriaNota);
        if (auditoriaNota.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auditoriaNota.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auditoriaNotaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuditoriaNota> result = auditoriaNotaRepository
            .findById(auditoriaNota.getId())
            .map(existingAuditoriaNota -> {
                if (auditoriaNota.getAuditoriaId() != null) {
                    existingAuditoriaNota.setAuditoriaId(auditoriaNota.getAuditoriaId());
                }
                if (auditoriaNota.getNotaAnterior() != null) {
                    existingAuditoriaNota.setNotaAnterior(auditoriaNota.getNotaAnterior());
                }
                if (auditoriaNota.getNotaNueva() != null) {
                    existingAuditoriaNota.setNotaNueva(auditoriaNota.getNotaNueva());
                }
                if (auditoriaNota.getFechaModificacion() != null) {
                    existingAuditoriaNota.setFechaModificacion(auditoriaNota.getFechaModificacion());
                }
                if (auditoriaNota.getIp() != null) {
                    existingAuditoriaNota.setIp(auditoriaNota.getIp());
                }
                if (auditoriaNota.getUsuarioPortal() != null) {
                    existingAuditoriaNota.setUsuarioPortal(auditoriaNota.getUsuarioPortal());
                }

                return existingAuditoriaNota;
            })
            .map(auditoriaNotaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditoriaNota.getId().toString())
        );
    }

    /**
     * {@code GET  /auditoria-notas} : get all the auditoriaNotas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditoriaNotas in body.
     */
    @GetMapping("/auditoria-notas")
    public List<AuditoriaNota> getAllAuditoriaNotas() {
        log.debug("REST request to get all AuditoriaNotas");
        return auditoriaNotaRepository.findAll();
    }

    /**
     * {@code GET  /auditoria-notas/:id} : get the "id" auditoriaNota.
     *
     * @param id the id of the auditoriaNota to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditoriaNota, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/auditoria-notas/{id}")
    public ResponseEntity<AuditoriaNota> getAuditoriaNota(@PathVariable Long id) {
        log.debug("REST request to get AuditoriaNota : {}", id);
        Optional<AuditoriaNota> auditoriaNota = auditoriaNotaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(auditoriaNota);
    }

    /**
     * {@code DELETE  /auditoria-notas/:id} : delete the "id" auditoriaNota.
     *
     * @param id the id of the auditoriaNota to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/auditoria-notas/{id}")
    public ResponseEntity<Void> deleteAuditoriaNota(@PathVariable Long id) {
        log.debug("REST request to delete AuditoriaNota : {}", id);
        auditoriaNotaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
