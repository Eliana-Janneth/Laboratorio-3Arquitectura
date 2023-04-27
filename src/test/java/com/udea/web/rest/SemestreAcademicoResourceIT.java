package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.SemestreAcademico;
import com.udea.repository.SemestreAcademicoRepository;
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
 * Integration tests for the {@link SemestreAcademicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SemestreAcademicoResourceIT {

    private static final Integer DEFAULT_SEMESTRE_ACADEMICO_ID = 1;
    private static final Integer UPDATED_SEMESTRE_ACADEMICO_ID = 2;

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Integer DEFAULT_ANIO = 1;
    private static final Integer UPDATED_ANIO = 2;

    private static final Integer DEFAULT_PERIODO_ACADEMICO = 1;
    private static final Integer UPDATED_PERIODO_ACADEMICO = 2;

    private static final String ENTITY_API_URL = "/api/semestre-academicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SemestreAcademicoRepository semestreAcademicoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSemestreAcademicoMockMvc;

    private SemestreAcademico semestreAcademico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SemestreAcademico createEntity(EntityManager em) {
        SemestreAcademico semestreAcademico = new SemestreAcademico()
            .semestreAcademicoId(DEFAULT_SEMESTRE_ACADEMICO_ID)
            .codigo(DEFAULT_CODIGO)
            .estado(DEFAULT_ESTADO)
            .anio(DEFAULT_ANIO)
            .periodoAcademico(DEFAULT_PERIODO_ACADEMICO);
        return semestreAcademico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SemestreAcademico createUpdatedEntity(EntityManager em) {
        SemestreAcademico semestreAcademico = new SemestreAcademico()
            .semestreAcademicoId(UPDATED_SEMESTRE_ACADEMICO_ID)
            .codigo(UPDATED_CODIGO)
            .estado(UPDATED_ESTADO)
            .anio(UPDATED_ANIO)
            .periodoAcademico(UPDATED_PERIODO_ACADEMICO);
        return semestreAcademico;
    }

    @BeforeEach
    public void initTest() {
        semestreAcademico = createEntity(em);
    }

    @Test
    @Transactional
    void createSemestreAcademico() throws Exception {
        int databaseSizeBeforeCreate = semestreAcademicoRepository.findAll().size();
        // Create the SemestreAcademico
        restSemestreAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semestreAcademico))
            )
            .andExpect(status().isCreated());

        // Validate the SemestreAcademico in the database
        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeCreate + 1);
        SemestreAcademico testSemestreAcademico = semestreAcademicoList.get(semestreAcademicoList.size() - 1);
        assertThat(testSemestreAcademico.getSemestreAcademicoId()).isEqualTo(DEFAULT_SEMESTRE_ACADEMICO_ID);
        assertThat(testSemestreAcademico.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testSemestreAcademico.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testSemestreAcademico.getAnio()).isEqualTo(DEFAULT_ANIO);
        assertThat(testSemestreAcademico.getPeriodoAcademico()).isEqualTo(DEFAULT_PERIODO_ACADEMICO);
    }

    @Test
    @Transactional
    void createSemestreAcademicoWithExistingId() throws Exception {
        // Create the SemestreAcademico with an existing ID
        semestreAcademico.setId(1L);

        int databaseSizeBeforeCreate = semestreAcademicoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSemestreAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semestreAcademico))
            )
            .andExpect(status().isBadRequest());

        // Validate the SemestreAcademico in the database
        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSemestreAcademicoIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = semestreAcademicoRepository.findAll().size();
        // set the field null
        semestreAcademico.setSemestreAcademicoId(null);

        // Create the SemestreAcademico, which fails.

        restSemestreAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semestreAcademico))
            )
            .andExpect(status().isBadRequest());

        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = semestreAcademicoRepository.findAll().size();
        // set the field null
        semestreAcademico.setCodigo(null);

        // Create the SemestreAcademico, which fails.

        restSemestreAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semestreAcademico))
            )
            .andExpect(status().isBadRequest());

        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = semestreAcademicoRepository.findAll().size();
        // set the field null
        semestreAcademico.setEstado(null);

        // Create the SemestreAcademico, which fails.

        restSemestreAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semestreAcademico))
            )
            .andExpect(status().isBadRequest());

        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnioIsRequired() throws Exception {
        int databaseSizeBeforeTest = semestreAcademicoRepository.findAll().size();
        // set the field null
        semestreAcademico.setAnio(null);

        // Create the SemestreAcademico, which fails.

        restSemestreAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semestreAcademico))
            )
            .andExpect(status().isBadRequest());

        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPeriodoAcademicoIsRequired() throws Exception {
        int databaseSizeBeforeTest = semestreAcademicoRepository.findAll().size();
        // set the field null
        semestreAcademico.setPeriodoAcademico(null);

        // Create the SemestreAcademico, which fails.

        restSemestreAcademicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semestreAcademico))
            )
            .andExpect(status().isBadRequest());

        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSemestreAcademicos() throws Exception {
        // Initialize the database
        semestreAcademicoRepository.saveAndFlush(semestreAcademico);

        // Get all the semestreAcademicoList
        restSemestreAcademicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(semestreAcademico.getId().intValue())))
            .andExpect(jsonPath("$.[*].semestreAcademicoId").value(hasItem(DEFAULT_SEMESTRE_ACADEMICO_ID)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].anio").value(hasItem(DEFAULT_ANIO)))
            .andExpect(jsonPath("$.[*].periodoAcademico").value(hasItem(DEFAULT_PERIODO_ACADEMICO)));
    }

    @Test
    @Transactional
    void getSemestreAcademico() throws Exception {
        // Initialize the database
        semestreAcademicoRepository.saveAndFlush(semestreAcademico);

        // Get the semestreAcademico
        restSemestreAcademicoMockMvc
            .perform(get(ENTITY_API_URL_ID, semestreAcademico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(semestreAcademico.getId().intValue()))
            .andExpect(jsonPath("$.semestreAcademicoId").value(DEFAULT_SEMESTRE_ACADEMICO_ID))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.anio").value(DEFAULT_ANIO))
            .andExpect(jsonPath("$.periodoAcademico").value(DEFAULT_PERIODO_ACADEMICO));
    }

    @Test
    @Transactional
    void getNonExistingSemestreAcademico() throws Exception {
        // Get the semestreAcademico
        restSemestreAcademicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSemestreAcademico() throws Exception {
        // Initialize the database
        semestreAcademicoRepository.saveAndFlush(semestreAcademico);

        int databaseSizeBeforeUpdate = semestreAcademicoRepository.findAll().size();

        // Update the semestreAcademico
        SemestreAcademico updatedSemestreAcademico = semestreAcademicoRepository.findById(semestreAcademico.getId()).get();
        // Disconnect from session so that the updates on updatedSemestreAcademico are not directly saved in db
        em.detach(updatedSemestreAcademico);
        updatedSemestreAcademico
            .semestreAcademicoId(UPDATED_SEMESTRE_ACADEMICO_ID)
            .codigo(UPDATED_CODIGO)
            .estado(UPDATED_ESTADO)
            .anio(UPDATED_ANIO)
            .periodoAcademico(UPDATED_PERIODO_ACADEMICO);

        restSemestreAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSemestreAcademico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSemestreAcademico))
            )
            .andExpect(status().isOk());

        // Validate the SemestreAcademico in the database
        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeUpdate);
        SemestreAcademico testSemestreAcademico = semestreAcademicoList.get(semestreAcademicoList.size() - 1);
        assertThat(testSemestreAcademico.getSemestreAcademicoId()).isEqualTo(UPDATED_SEMESTRE_ACADEMICO_ID);
        assertThat(testSemestreAcademico.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testSemestreAcademico.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testSemestreAcademico.getAnio()).isEqualTo(UPDATED_ANIO);
        assertThat(testSemestreAcademico.getPeriodoAcademico()).isEqualTo(UPDATED_PERIODO_ACADEMICO);
    }

    @Test
    @Transactional
    void putNonExistingSemestreAcademico() throws Exception {
        int databaseSizeBeforeUpdate = semestreAcademicoRepository.findAll().size();
        semestreAcademico.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSemestreAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, semestreAcademico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(semestreAcademico))
            )
            .andExpect(status().isBadRequest());

        // Validate the SemestreAcademico in the database
        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSemestreAcademico() throws Exception {
        int databaseSizeBeforeUpdate = semestreAcademicoRepository.findAll().size();
        semestreAcademico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSemestreAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(semestreAcademico))
            )
            .andExpect(status().isBadRequest());

        // Validate the SemestreAcademico in the database
        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSemestreAcademico() throws Exception {
        int databaseSizeBeforeUpdate = semestreAcademicoRepository.findAll().size();
        semestreAcademico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSemestreAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semestreAcademico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SemestreAcademico in the database
        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSemestreAcademicoWithPatch() throws Exception {
        // Initialize the database
        semestreAcademicoRepository.saveAndFlush(semestreAcademico);

        int databaseSizeBeforeUpdate = semestreAcademicoRepository.findAll().size();

        // Update the semestreAcademico using partial update
        SemestreAcademico partialUpdatedSemestreAcademico = new SemestreAcademico();
        partialUpdatedSemestreAcademico.setId(semestreAcademico.getId());

        partialUpdatedSemestreAcademico.semestreAcademicoId(UPDATED_SEMESTRE_ACADEMICO_ID).anio(UPDATED_ANIO);

        restSemestreAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSemestreAcademico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSemestreAcademico))
            )
            .andExpect(status().isOk());

        // Validate the SemestreAcademico in the database
        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeUpdate);
        SemestreAcademico testSemestreAcademico = semestreAcademicoList.get(semestreAcademicoList.size() - 1);
        assertThat(testSemestreAcademico.getSemestreAcademicoId()).isEqualTo(UPDATED_SEMESTRE_ACADEMICO_ID);
        assertThat(testSemestreAcademico.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testSemestreAcademico.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testSemestreAcademico.getAnio()).isEqualTo(UPDATED_ANIO);
        assertThat(testSemestreAcademico.getPeriodoAcademico()).isEqualTo(DEFAULT_PERIODO_ACADEMICO);
    }

    @Test
    @Transactional
    void fullUpdateSemestreAcademicoWithPatch() throws Exception {
        // Initialize the database
        semestreAcademicoRepository.saveAndFlush(semestreAcademico);

        int databaseSizeBeforeUpdate = semestreAcademicoRepository.findAll().size();

        // Update the semestreAcademico using partial update
        SemestreAcademico partialUpdatedSemestreAcademico = new SemestreAcademico();
        partialUpdatedSemestreAcademico.setId(semestreAcademico.getId());

        partialUpdatedSemestreAcademico
            .semestreAcademicoId(UPDATED_SEMESTRE_ACADEMICO_ID)
            .codigo(UPDATED_CODIGO)
            .estado(UPDATED_ESTADO)
            .anio(UPDATED_ANIO)
            .periodoAcademico(UPDATED_PERIODO_ACADEMICO);

        restSemestreAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSemestreAcademico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSemestreAcademico))
            )
            .andExpect(status().isOk());

        // Validate the SemestreAcademico in the database
        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeUpdate);
        SemestreAcademico testSemestreAcademico = semestreAcademicoList.get(semestreAcademicoList.size() - 1);
        assertThat(testSemestreAcademico.getSemestreAcademicoId()).isEqualTo(UPDATED_SEMESTRE_ACADEMICO_ID);
        assertThat(testSemestreAcademico.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testSemestreAcademico.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testSemestreAcademico.getAnio()).isEqualTo(UPDATED_ANIO);
        assertThat(testSemestreAcademico.getPeriodoAcademico()).isEqualTo(UPDATED_PERIODO_ACADEMICO);
    }

    @Test
    @Transactional
    void patchNonExistingSemestreAcademico() throws Exception {
        int databaseSizeBeforeUpdate = semestreAcademicoRepository.findAll().size();
        semestreAcademico.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSemestreAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, semestreAcademico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(semestreAcademico))
            )
            .andExpect(status().isBadRequest());

        // Validate the SemestreAcademico in the database
        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSemestreAcademico() throws Exception {
        int databaseSizeBeforeUpdate = semestreAcademicoRepository.findAll().size();
        semestreAcademico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSemestreAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(semestreAcademico))
            )
            .andExpect(status().isBadRequest());

        // Validate the SemestreAcademico in the database
        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSemestreAcademico() throws Exception {
        int databaseSizeBeforeUpdate = semestreAcademicoRepository.findAll().size();
        semestreAcademico.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSemestreAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(semestreAcademico))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SemestreAcademico in the database
        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSemestreAcademico() throws Exception {
        // Initialize the database
        semestreAcademicoRepository.saveAndFlush(semestreAcademico);

        int databaseSizeBeforeDelete = semestreAcademicoRepository.findAll().size();

        // Delete the semestreAcademico
        restSemestreAcademicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, semestreAcademico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SemestreAcademico> semestreAcademicoList = semestreAcademicoRepository.findAll();
        assertThat(semestreAcademicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
