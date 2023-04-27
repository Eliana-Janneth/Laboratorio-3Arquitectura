package com.udea.web.rest;

import com.udea.domain.SemestreAcademico;
import com.udea.repository.SemestreAcademicoRepository;
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
 * REST controller for managing {@link com.udea.domain.SemestreAcademico}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SemestreAcademicoResource {

    private final Logger log = LoggerFactory.getLogger(SemestreAcademicoResource.class);

    private static final String ENTITY_NAME = "semestreAcademico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SemestreAcademicoRepository semestreAcademicoRepository;

    public SemestreAcademicoResource(SemestreAcademicoRepository semestreAcademicoRepository) {
        this.semestreAcademicoRepository = semestreAcademicoRepository;
    }

    /**
     * {@code POST  /semestre-academicos} : Create a new semestreAcademico.
     *
     * @param semestreAcademico the semestreAcademico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new semestreAcademico, or with status {@code 400 (Bad Request)} if the semestreAcademico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/semestre-academicos")
    public ResponseEntity<SemestreAcademico> createSemestreAcademico(@Valid @RequestBody SemestreAcademico semestreAcademico)
        throws URISyntaxException {
        log.debug("REST request to save SemestreAcademico : {}", semestreAcademico);
        if (semestreAcademico.getId() != null) {
            throw new BadRequestAlertException("A new semestreAcademico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SemestreAcademico result = semestreAcademicoRepository.save(semestreAcademico);
        return ResponseEntity
            .created(new URI("/api/semestre-academicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /semestre-academicos/:id} : Updates an existing semestreAcademico.
     *
     * @param id the id of the semestreAcademico to save.
     * @param semestreAcademico the semestreAcademico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated semestreAcademico,
     * or with status {@code 400 (Bad Request)} if the semestreAcademico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the semestreAcademico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/semestre-academicos/{id}")
    public ResponseEntity<SemestreAcademico> updateSemestreAcademico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SemestreAcademico semestreAcademico
    ) throws URISyntaxException {
        log.debug("REST request to update SemestreAcademico : {}, {}", id, semestreAcademico);
        if (semestreAcademico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, semestreAcademico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!semestreAcademicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SemestreAcademico result = semestreAcademicoRepository.save(semestreAcademico);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, semestreAcademico.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /semestre-academicos/:id} : Partial updates given fields of an existing semestreAcademico, field will ignore if it is null
     *
     * @param id the id of the semestreAcademico to save.
     * @param semestreAcademico the semestreAcademico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated semestreAcademico,
     * or with status {@code 400 (Bad Request)} if the semestreAcademico is not valid,
     * or with status {@code 404 (Not Found)} if the semestreAcademico is not found,
     * or with status {@code 500 (Internal Server Error)} if the semestreAcademico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/semestre-academicos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SemestreAcademico> partialUpdateSemestreAcademico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SemestreAcademico semestreAcademico
    ) throws URISyntaxException {
        log.debug("REST request to partial update SemestreAcademico partially : {}, {}", id, semestreAcademico);
        if (semestreAcademico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, semestreAcademico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!semestreAcademicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SemestreAcademico> result = semestreAcademicoRepository
            .findById(semestreAcademico.getId())
            .map(existingSemestreAcademico -> {
                if (semestreAcademico.getSemestreAcademicoId() != null) {
                    existingSemestreAcademico.setSemestreAcademicoId(semestreAcademico.getSemestreAcademicoId());
                }
                if (semestreAcademico.getCodigo() != null) {
                    existingSemestreAcademico.setCodigo(semestreAcademico.getCodigo());
                }
                if (semestreAcademico.getEstado() != null) {
                    existingSemestreAcademico.setEstado(semestreAcademico.getEstado());
                }
                if (semestreAcademico.getAnio() != null) {
                    existingSemestreAcademico.setAnio(semestreAcademico.getAnio());
                }
                if (semestreAcademico.getPeriodoAcademico() != null) {
                    existingSemestreAcademico.setPeriodoAcademico(semestreAcademico.getPeriodoAcademico());
                }

                return existingSemestreAcademico;
            })
            .map(semestreAcademicoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, semestreAcademico.getId().toString())
        );
    }

    /**
     * {@code GET  /semestre-academicos} : get all the semestreAcademicos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of semestreAcademicos in body.
     */
    @GetMapping("/semestre-academicos")
    public List<SemestreAcademico> getAllSemestreAcademicos() {
        log.debug("REST request to get all SemestreAcademicos");
        return semestreAcademicoRepository.findAll();
    }

    /**
     * {@code GET  /semestre-academicos/:id} : get the "id" semestreAcademico.
     *
     * @param id the id of the semestreAcademico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the semestreAcademico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/semestre-academicos/{id}")
    public ResponseEntity<SemestreAcademico> getSemestreAcademico(@PathVariable Long id) {
        log.debug("REST request to get SemestreAcademico : {}", id);
        Optional<SemestreAcademico> semestreAcademico = semestreAcademicoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(semestreAcademico);
    }

    /**
     * {@code DELETE  /semestre-academicos/:id} : delete the "id" semestreAcademico.
     *
     * @param id the id of the semestreAcademico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/semestre-academicos/{id}")
    public ResponseEntity<Void> deleteSemestreAcademico(@PathVariable Long id) {
        log.debug("REST request to delete SemestreAcademico : {}", id);
        semestreAcademicoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
