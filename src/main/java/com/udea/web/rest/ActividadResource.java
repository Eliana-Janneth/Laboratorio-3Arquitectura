package com.udea.web.rest;

import com.udea.domain.Actividad;
import com.udea.repository.ActividadRepository;
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
 * REST controller for managing {@link com.udea.domain.Actividad}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ActividadResource {

    private final Logger log = LoggerFactory.getLogger(ActividadResource.class);

    private static final String ENTITY_NAME = "actividad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActividadRepository actividadRepository;

    public ActividadResource(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    /**
     * {@code POST  /actividads} : Create a new actividad.
     *
     * @param actividad the actividad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actividad, or with status {@code 400 (Bad Request)} if the actividad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/actividads")
    public ResponseEntity<Actividad> createActividad(@Valid @RequestBody Actividad actividad) throws URISyntaxException {
        log.debug("REST request to save Actividad : {}", actividad);
        if (actividad.getId() != null) {
            throw new BadRequestAlertException("A new actividad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Actividad result = actividadRepository.save(actividad);
        return ResponseEntity
            .created(new URI("/api/actividads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /actividads/:id} : Updates an existing actividad.
     *
     * @param id the id of the actividad to save.
     * @param actividad the actividad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actividad,
     * or with status {@code 400 (Bad Request)} if the actividad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actividad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/actividads/{id}")
    public ResponseEntity<Actividad> updateActividad(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Actividad actividad
    ) throws URISyntaxException {
        log.debug("REST request to update Actividad : {}, {}", id, actividad);
        if (actividad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actividad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actividadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Actividad result = actividadRepository.save(actividad);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actividad.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /actividads/:id} : Partial updates given fields of an existing actividad, field will ignore if it is null
     *
     * @param id the id of the actividad to save.
     * @param actividad the actividad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actividad,
     * or with status {@code 400 (Bad Request)} if the actividad is not valid,
     * or with status {@code 404 (Not Found)} if the actividad is not found,
     * or with status {@code 500 (Internal Server Error)} if the actividad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/actividads/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Actividad> partialUpdateActividad(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Actividad actividad
    ) throws URISyntaxException {
        log.debug("REST request to partial update Actividad partially : {}, {}", id, actividad);
        if (actividad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actividad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actividadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Actividad> result = actividadRepository
            .findById(actividad.getId())
            .map(existingActividad -> {
                if (actividad.getActividadId() != null) {
                    existingActividad.setActividadId(actividad.getActividadId());
                }
                if (actividad.getDescripcion() != null) {
                    existingActividad.setDescripcion(actividad.getDescripcion());
                }
                if (actividad.getPorcentaje() != null) {
                    existingActividad.setPorcentaje(actividad.getPorcentaje());
                }

                return existingActividad;
            })
            .map(actividadRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actividad.getId().toString())
        );
    }

    /**
     * {@code GET  /actividads} : get all the actividads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actividads in body.
     */
    @GetMapping("/actividads")
    public List<Actividad> getAllActividads() {
        log.debug("REST request to get all Actividads");
        return actividadRepository.findAll();
    }

    /**
     * {@code GET  /actividads/:id} : get the "id" actividad.
     *
     * @param id the id of the actividad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actividad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/actividads/{id}")
    public ResponseEntity<Actividad> getActividad(@PathVariable Long id) {
        log.debug("REST request to get Actividad : {}", id);
        Optional<Actividad> actividad = actividadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(actividad);
    }

    /**
     * {@code DELETE  /actividads/:id} : delete the "id" actividad.
     *
     * @param id the id of the actividad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/actividads/{id}")
    public ResponseEntity<Void> deleteActividad(@PathVariable Long id) {
        log.debug("REST request to delete Actividad : {}", id);
        actividadRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
