package com.udea.web.rest;

import com.udea.domain.GrupoEstudiante;
import com.udea.repository.GrupoEstudianteRepository;
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
 * REST controller for managing {@link com.udea.domain.GrupoEstudiante}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GrupoEstudianteResource {

    private final Logger log = LoggerFactory.getLogger(GrupoEstudianteResource.class);

    private static final String ENTITY_NAME = "grupoEstudiante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoEstudianteRepository grupoEstudianteRepository;

    public GrupoEstudianteResource(GrupoEstudianteRepository grupoEstudianteRepository) {
        this.grupoEstudianteRepository = grupoEstudianteRepository;
    }

    /**
     * {@code POST  /grupo-estudiantes} : Create a new grupoEstudiante.
     *
     * @param grupoEstudiante the grupoEstudiante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoEstudiante, or with status {@code 400 (Bad Request)} if the grupoEstudiante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grupo-estudiantes")
    public ResponseEntity<GrupoEstudiante> createGrupoEstudiante(@Valid @RequestBody GrupoEstudiante grupoEstudiante)
        throws URISyntaxException {
        log.debug("REST request to save GrupoEstudiante : {}", grupoEstudiante);
        if (grupoEstudiante.getId() != null) {
            throw new BadRequestAlertException("A new grupoEstudiante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoEstudiante result = grupoEstudianteRepository.save(grupoEstudiante);
        return ResponseEntity
            .created(new URI("/api/grupo-estudiantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grupo-estudiantes/:id} : Updates an existing grupoEstudiante.
     *
     * @param id the id of the grupoEstudiante to save.
     * @param grupoEstudiante the grupoEstudiante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoEstudiante,
     * or with status {@code 400 (Bad Request)} if the grupoEstudiante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoEstudiante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grupo-estudiantes/{id}")
    public ResponseEntity<GrupoEstudiante> updateGrupoEstudiante(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GrupoEstudiante grupoEstudiante
    ) throws URISyntaxException {
        log.debug("REST request to update GrupoEstudiante : {}, {}", id, grupoEstudiante);
        if (grupoEstudiante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoEstudiante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoEstudianteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GrupoEstudiante result = grupoEstudianteRepository.save(grupoEstudiante);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoEstudiante.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /grupo-estudiantes/:id} : Partial updates given fields of an existing grupoEstudiante, field will ignore if it is null
     *
     * @param id the id of the grupoEstudiante to save.
     * @param grupoEstudiante the grupoEstudiante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoEstudiante,
     * or with status {@code 400 (Bad Request)} if the grupoEstudiante is not valid,
     * or with status {@code 404 (Not Found)} if the grupoEstudiante is not found,
     * or with status {@code 500 (Internal Server Error)} if the grupoEstudiante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/grupo-estudiantes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GrupoEstudiante> partialUpdateGrupoEstudiante(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GrupoEstudiante grupoEstudiante
    ) throws URISyntaxException {
        log.debug("REST request to partial update GrupoEstudiante partially : {}, {}", id, grupoEstudiante);
        if (grupoEstudiante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoEstudiante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoEstudianteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GrupoEstudiante> result = grupoEstudianteRepository
            .findById(grupoEstudiante.getId())
            .map(existingGrupoEstudiante -> {
                if (grupoEstudiante.getGrupoEstudianteId() != null) {
                    existingGrupoEstudiante.setGrupoEstudianteId(grupoEstudiante.getGrupoEstudianteId());
                }

                return existingGrupoEstudiante;
            })
            .map(grupoEstudianteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoEstudiante.getId().toString())
        );
    }

    /**
     * {@code GET  /grupo-estudiantes} : get all the grupoEstudiantes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoEstudiantes in body.
     */
    @GetMapping("/grupo-estudiantes")
    public List<GrupoEstudiante> getAllGrupoEstudiantes() {
        log.debug("REST request to get all GrupoEstudiantes");
        return grupoEstudianteRepository.findAll();
    }

    /**
     * {@code GET  /grupo-estudiantes/:id} : get the "id" grupoEstudiante.
     *
     * @param id the id of the grupoEstudiante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoEstudiante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupo-estudiantes/{id}")
    public ResponseEntity<GrupoEstudiante> getGrupoEstudiante(@PathVariable Long id) {
        log.debug("REST request to get GrupoEstudiante : {}", id);
        Optional<GrupoEstudiante> grupoEstudiante = grupoEstudianteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(grupoEstudiante);
    }

    /**
     * {@code DELETE  /grupo-estudiantes/:id} : delete the "id" grupoEstudiante.
     *
     * @param id the id of the grupoEstudiante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grupo-estudiantes/{id}")
    public ResponseEntity<Void> deleteGrupoEstudiante(@PathVariable Long id) {
        log.debug("REST request to delete GrupoEstudiante : {}", id);
        grupoEstudianteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
