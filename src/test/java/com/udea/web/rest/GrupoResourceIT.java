package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.Grupo;
import com.udea.repository.GrupoRepository;
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
 * Integration tests for the {@link GrupoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GrupoResourceIT {

    private static final Integer DEFAULT_GRUPO_ID = 1;
    private static final Integer UPDATED_GRUPO_ID = 2;

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_HORARIO = "AAAAAAAAAA";
    private static final String UPDATED_HORARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/grupos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoMockMvc;

    private Grupo grupo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grupo createEntity(EntityManager em) {
        Grupo grupo = new Grupo().grupoId(DEFAULT_GRUPO_ID).codigo(DEFAULT_CODIGO).horario(DEFAULT_HORARIO);
        return grupo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grupo createUpdatedEntity(EntityManager em) {
        Grupo grupo = new Grupo().grupoId(UPDATED_GRUPO_ID).codigo(UPDATED_CODIGO).horario(UPDATED_HORARIO);
        return grupo;
    }

    @BeforeEach
    public void initTest() {
        grupo = createEntity(em);
    }

    @Test
    @Transactional
    void createGrupo() throws Exception {
        int databaseSizeBeforeCreate = grupoRepository.findAll().size();
        // Create the Grupo
        restGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupo)))
            .andExpect(status().isCreated());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeCreate + 1);
        Grupo testGrupo = grupoList.get(grupoList.size() - 1);
        assertThat(testGrupo.getGrupoId()).isEqualTo(DEFAULT_GRUPO_ID);
        assertThat(testGrupo.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testGrupo.getHorario()).isEqualTo(DEFAULT_HORARIO);
    }

    @Test
    @Transactional
    void createGrupoWithExistingId() throws Exception {
        // Create the Grupo with an existing ID
        grupo.setId(1L);

        int databaseSizeBeforeCreate = grupoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupo)))
            .andExpect(status().isBadRequest());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGrupoIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoRepository.findAll().size();
        // set the field null
        grupo.setGrupoId(null);

        // Create the Grupo, which fails.

        restGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupo)))
            .andExpect(status().isBadRequest());

        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupoRepository.findAll().size();
        // set the field null
        grupo.setCodigo(null);

        // Create the Grupo, which fails.

        restGrupoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupo)))
            .andExpect(status().isBadRequest());

        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGrupos() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

        // Get all the grupoList
        restGrupoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupo.getId().intValue())))
            .andExpect(jsonPath("$.[*].grupoId").value(hasItem(DEFAULT_GRUPO_ID)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO)));
    }

    @Test
    @Transactional
    void getGrupo() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

        // Get the grupo
        restGrupoMockMvc
            .perform(get(ENTITY_API_URL_ID, grupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupo.getId().intValue()))
            .andExpect(jsonPath("$.grupoId").value(DEFAULT_GRUPO_ID))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO));
    }

    @Test
    @Transactional
    void getNonExistingGrupo() throws Exception {
        // Get the grupo
        restGrupoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGrupo() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

        int databaseSizeBeforeUpdate = grupoRepository.findAll().size();

        // Update the grupo
        Grupo updatedGrupo = grupoRepository.findById(grupo.getId()).get();
        // Disconnect from session so that the updates on updatedGrupo are not directly saved in db
        em.detach(updatedGrupo);
        updatedGrupo.grupoId(UPDATED_GRUPO_ID).codigo(UPDATED_CODIGO).horario(UPDATED_HORARIO);

        restGrupoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrupo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGrupo))
            )
            .andExpect(status().isOk());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeUpdate);
        Grupo testGrupo = grupoList.get(grupoList.size() - 1);
        assertThat(testGrupo.getGrupoId()).isEqualTo(UPDATED_GRUPO_ID);
        assertThat(testGrupo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testGrupo.getHorario()).isEqualTo(UPDATED_HORARIO);
    }

    @Test
    @Transactional
    void putNonExistingGrupo() throws Exception {
        int databaseSizeBeforeUpdate = grupoRepository.findAll().size();
        grupo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(grupo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrupo() throws Exception {
        int databaseSizeBeforeUpdate = grupoRepository.findAll().size();
        grupo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(grupo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrupo() throws Exception {
        int databaseSizeBeforeUpdate = grupoRepository.findAll().size();
        grupo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(grupo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrupoWithPatch() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

        int databaseSizeBeforeUpdate = grupoRepository.findAll().size();

        // Update the grupo using partial update
        Grupo partialUpdatedGrupo = new Grupo();
        partialUpdatedGrupo.setId(grupo.getId());

        partialUpdatedGrupo.grupoId(UPDATED_GRUPO_ID).horario(UPDATED_HORARIO);

        restGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrupo))
            )
            .andExpect(status().isOk());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeUpdate);
        Grupo testGrupo = grupoList.get(grupoList.size() - 1);
        assertThat(testGrupo.getGrupoId()).isEqualTo(UPDATED_GRUPO_ID);
        assertThat(testGrupo.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testGrupo.getHorario()).isEqualTo(UPDATED_HORARIO);
    }

    @Test
    @Transactional
    void fullUpdateGrupoWithPatch() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

        int databaseSizeBeforeUpdate = grupoRepository.findAll().size();

        // Update the grupo using partial update
        Grupo partialUpdatedGrupo = new Grupo();
        partialUpdatedGrupo.setId(grupo.getId());

        partialUpdatedGrupo.grupoId(UPDATED_GRUPO_ID).codigo(UPDATED_CODIGO).horario(UPDATED_HORARIO);

        restGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrupo))
            )
            .andExpect(status().isOk());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeUpdate);
        Grupo testGrupo = grupoList.get(grupoList.size() - 1);
        assertThat(testGrupo.getGrupoId()).isEqualTo(UPDATED_GRUPO_ID);
        assertThat(testGrupo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testGrupo.getHorario()).isEqualTo(UPDATED_HORARIO);
    }

    @Test
    @Transactional
    void patchNonExistingGrupo() throws Exception {
        int databaseSizeBeforeUpdate = grupoRepository.findAll().size();
        grupo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrupo() throws Exception {
        int databaseSizeBeforeUpdate = grupoRepository.findAll().size();
        grupo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(grupo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrupo() throws Exception {
        int databaseSizeBeforeUpdate = grupoRepository.findAll().size();
        grupo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(grupo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grupo in the database
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrupo() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

        int databaseSizeBeforeDelete = grupoRepository.findAll().size();

        // Delete the grupo
        restGrupoMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Grupo> grupoList = grupoRepository.findAll();
        assertThat(grupoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
