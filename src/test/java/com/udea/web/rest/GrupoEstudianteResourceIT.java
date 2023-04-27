package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.GrupoEstudiante;
import com.udea.repository.GrupoEstudianteRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GrupoEstudianteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GrupoEstudianteResourceIT {

    private static final Integer DEFAULT_GRUPO_ESTUDIANTE_ID = 1;
    private static final Integer UPDATED_GRUPO_ESTUDIANTE_ID = 2;

    private static final String ENTITY_API_URL = "/api/grupo-estudiantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GrupoEstudianteRepository grupoEstudianteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoEstudianteMockMvc;

    private GrupoEstudiante grupoEstudiante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoEstudiante createEntity(EntityManager em) {
        GrupoEstudiante grupoEstudiante = new GrupoEstudiante().grupoEstudianteId(DEFAULT_GRUPO_ESTUDIANTE_ID);
        return grupoEstudiante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoEstudiante createUpdatedEntity(EntityManager em) {
        GrupoEstudiante grupoEstudiante = new GrupoEstudiante().grupoEstudianteId(UPDATED_GRUPO_ESTUDIANTE_ID);
        return grupoEstudiante;
    }

    @BeforeEach
    public void initTest() {
        grupoEstudiante = createEntity(em);
    }

    @Test
    @Transactional
    void createGrupoEstudiante() throws Exception {
        int databaseSizeBeforeCreate = grupoEstudianteRepository.findAll().size();
        // Create the GrupoEstudiante
        restGrupoEstudianteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoEstudiante))
            )
            .andExpect(status().isCreated());

        // Validate the GrupoEstudiante in the database
        List<GrupoEstudiante> grupoEstudianteList = grupoEstudianteRepository.findAll();
        assertThat(grupoEstudianteList).hasSize(databaseSizeBeforeCreate + 1);
        GrupoEstudiante testGrupoEstudiante = grupoEstudianteList.get(grupoEstudianteList.size() - 1);
        assertThat(testGrupoEstudiante.getGrupoEstudianteId()).isEqualTo(DEFAULT_GRUPO_ESTUDIANTE_ID);
    }

    @Test
    @Transactional
    void createGrupoEstudianteWithExistingId() throws Exception {
        // Create the GrupoEstudiante with an existing ID
        grupoEstudiante.setId(1L);

        int databaseSizeBeforeCreate = grupoEstudianteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoEstudianteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoEstudiante))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoEstudiante in the database
        List<GrupoEstudiante> grupoEstudianteList = grupoEstudianteRepository.findAll();
        assertThat(grupoEstudianteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGrupoEstudianteIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoEstudianteRepository.findAll().size();
        // set the field null
        grupoEstudiante.setGrupoEstudianteId(null);

        // Create the GrupoEstudiante, which fails.

        restGrupoEstudianteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoEstudiante))
            )
            .andExpect(status().isBadRequest());

        List<GrupoEstudiante> grupoEstudianteList = grupoEstudianteRepository.findAll();
        assertThat(grupoEstudianteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGrupoEstudiantes() throws Exception {
        // Initialize the database
        grupoEstudianteRepository.saveAndFlush(grupoEstudiante);

        // Get all the grupoEstudianteList
        restGrupoEstudianteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoEstudiante.getId().intValue())))
            .andExpect(jsonPath("$.[*].grupoEstudianteId").value(hasItem(DEFAULT_GRUPO_ESTUDIANTE_ID)));
    }

    @Test
    @Transactional
    void getGrupoEstudiante() throws Exception {
        // Initialize the database
        grupoEstudianteRepository.saveAndFlush(grupoEstudiante);

        // Get the grupoEstudiante
        restGrupoEstudianteMockMvc
            .perform(get(ENTITY_API_URL_ID, grupoEstudiante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoEstudiante.getId().intValue()))
            .andExpect(jsonPath("$.grupoEstudianteId").value(DEFAULT_GRUPO_ESTUDIANTE_ID));
    }

    @Test
    @Transactional
    void getNonExistingGrupoEstudiante() throws Exception {
        // Get the grupoEstudiante
        restGrupoEstudianteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGrupoEstudiante() throws Exception {
        // Initialize the database
        grupoEstudianteRepository.saveAndFlush(grupoEstudiante);

        int databaseSizeBeforeUpdate = grupoEstudianteRepository.findAll().size();

        // Update the grupoEstudiante
        GrupoEstudiante updatedGrupoEstudiante = grupoEstudianteRepository.findById(grupoEstudiante.getId()).get();
        // Disconnect from session so that the updates on updatedGrupoEstudiante are not directly saved in db
        em.detach(updatedGrupoEstudiante);
        updatedGrupoEstudiante.grupoEstudianteId(UPDATED_GRUPO_ESTUDIANTE_ID);

        restGrupoEstudianteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrupoEstudiante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGrupoEstudiante))
            )
            .andExpect(status().isOk());

        // Validate the GrupoEstudiante in the database
        List<GrupoEstudiante> grupoEstudianteList = grupoEstudianteRepository.findAll();
        assertThat(grupoEstudianteList).hasSize(databaseSizeBeforeUpdate);
        GrupoEstudiante testGrupoEstudiante = grupoEstudianteList.get(grupoEstudianteList.size() - 1);
        assertThat(testGrupoEstudiante.getGrupoEstudianteId()).isEqualTo(UPDATED_GRUPO_ESTUDIANTE_ID);
    }

    @Test
    @Transactional
    void putNonExistingGrupoEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = grupoEstudianteRepository.findAll().size();
        grupoEstudiante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoEstudianteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoEstudiante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(grupoEstudiante))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoEstudiante in the database
        List<GrupoEstudiante> grupoEstudianteList = grupoEstudianteRepository.findAll();
        assertThat(grupoEstudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrupoEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = grupoEstudianteRepository.findAll().size();
        grupoEstudiante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoEstudianteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(grupoEstudiante))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoEstudiante in the database
        List<GrupoEstudiante> grupoEstudianteList = grupoEstudianteRepository.findAll();
        assertThat(grupoEstudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrupoEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = grupoEstudianteRepository.findAll().size();
        grupoEstudiante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoEstudianteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupoEstudiante))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoEstudiante in the database
        List<GrupoEstudiante> grupoEstudianteList = grupoEstudianteRepository.findAll();
        assertThat(grupoEstudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrupoEstudianteWithPatch() throws Exception {
        // Initialize the database
        grupoEstudianteRepository.saveAndFlush(grupoEstudiante);

        int databaseSizeBeforeUpdate = grupoEstudianteRepository.findAll().size();

        // Update the grupoEstudiante using partial update
        GrupoEstudiante partialUpdatedGrupoEstudiante = new GrupoEstudiante();
        partialUpdatedGrupoEstudiante.setId(grupoEstudiante.getId());

        restGrupoEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoEstudiante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrupoEstudiante))
            )
            .andExpect(status().isOk());

        // Validate the GrupoEstudiante in the database
        List<GrupoEstudiante> grupoEstudianteList = grupoEstudianteRepository.findAll();
        assertThat(grupoEstudianteList).hasSize(databaseSizeBeforeUpdate);
        GrupoEstudiante testGrupoEstudiante = grupoEstudianteList.get(grupoEstudianteList.size() - 1);
        assertThat(testGrupoEstudiante.getGrupoEstudianteId()).isEqualTo(DEFAULT_GRUPO_ESTUDIANTE_ID);
    }

    @Test
    @Transactional
    void fullUpdateGrupoEstudianteWithPatch() throws Exception {
        // Initialize the database
        grupoEstudianteRepository.saveAndFlush(grupoEstudiante);

        int databaseSizeBeforeUpdate = grupoEstudianteRepository.findAll().size();

        // Update the grupoEstudiante using partial update
        GrupoEstudiante partialUpdatedGrupoEstudiante = new GrupoEstudiante();
        partialUpdatedGrupoEstudiante.setId(grupoEstudiante.getId());

        partialUpdatedGrupoEstudiante.grupoEstudianteId(UPDATED_GRUPO_ESTUDIANTE_ID);

        restGrupoEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoEstudiante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrupoEstudiante))
            )
            .andExpect(status().isOk());

        // Validate the GrupoEstudiante in the database
        List<GrupoEstudiante> grupoEstudianteList = grupoEstudianteRepository.findAll();
        assertThat(grupoEstudianteList).hasSize(databaseSizeBeforeUpdate);
        GrupoEstudiante testGrupoEstudiante = grupoEstudianteList.get(grupoEstudianteList.size() - 1);
        assertThat(testGrupoEstudiante.getGrupoEstudianteId()).isEqualTo(UPDATED_GRUPO_ESTUDIANTE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingGrupoEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = grupoEstudianteRepository.findAll().size();
        grupoEstudiante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupoEstudiante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupoEstudiante))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoEstudiante in the database
        List<GrupoEstudiante> grupoEstudianteList = grupoEstudianteRepository.findAll();
        assertThat(grupoEstudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrupoEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = grupoEstudianteRepository.findAll().size();
        grupoEstudiante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupoEstudiante))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoEstudiante in the database
        List<GrupoEstudiante> grupoEstudianteList = grupoEstudianteRepository.findAll();
        assertThat(grupoEstudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrupoEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = grupoEstudianteRepository.findAll().size();
        grupoEstudiante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupoEstudiante))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoEstudiante in the database
        List<GrupoEstudiante> grupoEstudianteList = grupoEstudianteRepository.findAll();
        assertThat(grupoEstudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrupoEstudiante() throws Exception {
        // Initialize the database
        grupoEstudianteRepository.saveAndFlush(grupoEstudiante);

        int databaseSizeBeforeDelete = grupoEstudianteRepository.findAll().size();

        // Delete the grupoEstudiante
        restGrupoEstudianteMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupoEstudiante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrupoEstudiante> grupoEstudianteList = grupoEstudianteRepository.findAll();
        assertThat(grupoEstudianteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
