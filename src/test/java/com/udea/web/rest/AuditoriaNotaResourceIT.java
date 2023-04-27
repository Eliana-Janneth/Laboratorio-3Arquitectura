package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.AuditoriaNota;
import com.udea.repository.AuditoriaNotaRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link AuditoriaNotaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuditoriaNotaResourceIT {

    private static final Integer DEFAULT_AUDITORIA_ID = 1;
    private static final Integer UPDATED_AUDITORIA_ID = 2;

    private static final Float DEFAULT_NOTA_ANTERIOR = 1F;
    private static final Float UPDATED_NOTA_ANTERIOR = 2F;

    private static final Float DEFAULT_NOTA_NUEVA = 1F;
    private static final Float UPDATED_NOTA_NUEVA = 2F;

    private static final LocalDate DEFAULT_FECHA_MODIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MODIFICACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO_PORTAL = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_PORTAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/auditoria-notas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuditoriaNotaRepository auditoriaNotaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuditoriaNotaMockMvc;

    private AuditoriaNota auditoriaNota;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditoriaNota createEntity(EntityManager em) {
        AuditoriaNota auditoriaNota = new AuditoriaNota()
            .auditoriaId(DEFAULT_AUDITORIA_ID)
            .notaAnterior(DEFAULT_NOTA_ANTERIOR)
            .notaNueva(DEFAULT_NOTA_NUEVA)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .ip(DEFAULT_IP)
            .usuarioPortal(DEFAULT_USUARIO_PORTAL);
        return auditoriaNota;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditoriaNota createUpdatedEntity(EntityManager em) {
        AuditoriaNota auditoriaNota = new AuditoriaNota()
            .auditoriaId(UPDATED_AUDITORIA_ID)
            .notaAnterior(UPDATED_NOTA_ANTERIOR)
            .notaNueva(UPDATED_NOTA_NUEVA)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .ip(UPDATED_IP)
            .usuarioPortal(UPDATED_USUARIO_PORTAL);
        return auditoriaNota;
    }

    @BeforeEach
    public void initTest() {
        auditoriaNota = createEntity(em);
    }

    @Test
    @Transactional
    void createAuditoriaNota() throws Exception {
        int databaseSizeBeforeCreate = auditoriaNotaRepository.findAll().size();
        // Create the AuditoriaNota
        restAuditoriaNotaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditoriaNota)))
            .andExpect(status().isCreated());

        // Validate the AuditoriaNota in the database
        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeCreate + 1);
        AuditoriaNota testAuditoriaNota = auditoriaNotaList.get(auditoriaNotaList.size() - 1);
        assertThat(testAuditoriaNota.getAuditoriaId()).isEqualTo(DEFAULT_AUDITORIA_ID);
        assertThat(testAuditoriaNota.getNotaAnterior()).isEqualTo(DEFAULT_NOTA_ANTERIOR);
        assertThat(testAuditoriaNota.getNotaNueva()).isEqualTo(DEFAULT_NOTA_NUEVA);
        assertThat(testAuditoriaNota.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testAuditoriaNota.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testAuditoriaNota.getUsuarioPortal()).isEqualTo(DEFAULT_USUARIO_PORTAL);
    }

    @Test
    @Transactional
    void createAuditoriaNotaWithExistingId() throws Exception {
        // Create the AuditoriaNota with an existing ID
        auditoriaNota.setId(1L);

        int databaseSizeBeforeCreate = auditoriaNotaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditoriaNotaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditoriaNota)))
            .andExpect(status().isBadRequest());

        // Validate the AuditoriaNota in the database
        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAuditoriaIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditoriaNotaRepository.findAll().size();
        // set the field null
        auditoriaNota.setAuditoriaId(null);

        // Create the AuditoriaNota, which fails.

        restAuditoriaNotaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditoriaNota)))
            .andExpect(status().isBadRequest());

        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotaAnteriorIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditoriaNotaRepository.findAll().size();
        // set the field null
        auditoriaNota.setNotaAnterior(null);

        // Create the AuditoriaNota, which fails.

        restAuditoriaNotaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditoriaNota)))
            .andExpect(status().isBadRequest());

        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotaNuevaIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditoriaNotaRepository.findAll().size();
        // set the field null
        auditoriaNota.setNotaNueva(null);

        // Create the AuditoriaNota, which fails.

        restAuditoriaNotaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditoriaNota)))
            .andExpect(status().isBadRequest());

        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaModificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditoriaNotaRepository.findAll().size();
        // set the field null
        auditoriaNota.setFechaModificacion(null);

        // Create the AuditoriaNota, which fails.

        restAuditoriaNotaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditoriaNota)))
            .andExpect(status().isBadRequest());

        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIpIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditoriaNotaRepository.findAll().size();
        // set the field null
        auditoriaNota.setIp(null);

        // Create the AuditoriaNota, which fails.

        restAuditoriaNotaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditoriaNota)))
            .andExpect(status().isBadRequest());

        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsuarioPortalIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditoriaNotaRepository.findAll().size();
        // set the field null
        auditoriaNota.setUsuarioPortal(null);

        // Create the AuditoriaNota, which fails.

        restAuditoriaNotaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditoriaNota)))
            .andExpect(status().isBadRequest());

        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAuditoriaNotas() throws Exception {
        // Initialize the database
        auditoriaNotaRepository.saveAndFlush(auditoriaNota);

        // Get all the auditoriaNotaList
        restAuditoriaNotaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditoriaNota.getId().intValue())))
            .andExpect(jsonPath("$.[*].auditoriaId").value(hasItem(DEFAULT_AUDITORIA_ID)))
            .andExpect(jsonPath("$.[*].notaAnterior").value(hasItem(DEFAULT_NOTA_ANTERIOR.doubleValue())))
            .andExpect(jsonPath("$.[*].notaNueva").value(hasItem(DEFAULT_NOTA_NUEVA.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].usuarioPortal").value(hasItem(DEFAULT_USUARIO_PORTAL)));
    }

    @Test
    @Transactional
    void getAuditoriaNota() throws Exception {
        // Initialize the database
        auditoriaNotaRepository.saveAndFlush(auditoriaNota);

        // Get the auditoriaNota
        restAuditoriaNotaMockMvc
            .perform(get(ENTITY_API_URL_ID, auditoriaNota.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auditoriaNota.getId().intValue()))
            .andExpect(jsonPath("$.auditoriaId").value(DEFAULT_AUDITORIA_ID))
            .andExpect(jsonPath("$.notaAnterior").value(DEFAULT_NOTA_ANTERIOR.doubleValue()))
            .andExpect(jsonPath("$.notaNueva").value(DEFAULT_NOTA_NUEVA.doubleValue()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP))
            .andExpect(jsonPath("$.usuarioPortal").value(DEFAULT_USUARIO_PORTAL));
    }

    @Test
    @Transactional
    void getNonExistingAuditoriaNota() throws Exception {
        // Get the auditoriaNota
        restAuditoriaNotaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAuditoriaNota() throws Exception {
        // Initialize the database
        auditoriaNotaRepository.saveAndFlush(auditoriaNota);

        int databaseSizeBeforeUpdate = auditoriaNotaRepository.findAll().size();

        // Update the auditoriaNota
        AuditoriaNota updatedAuditoriaNota = auditoriaNotaRepository.findById(auditoriaNota.getId()).get();
        // Disconnect from session so that the updates on updatedAuditoriaNota are not directly saved in db
        em.detach(updatedAuditoriaNota);
        updatedAuditoriaNota
            .auditoriaId(UPDATED_AUDITORIA_ID)
            .notaAnterior(UPDATED_NOTA_ANTERIOR)
            .notaNueva(UPDATED_NOTA_NUEVA)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .ip(UPDATED_IP)
            .usuarioPortal(UPDATED_USUARIO_PORTAL);

        restAuditoriaNotaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAuditoriaNota.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAuditoriaNota))
            )
            .andExpect(status().isOk());

        // Validate the AuditoriaNota in the database
        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeUpdate);
        AuditoriaNota testAuditoriaNota = auditoriaNotaList.get(auditoriaNotaList.size() - 1);
        assertThat(testAuditoriaNota.getAuditoriaId()).isEqualTo(UPDATED_AUDITORIA_ID);
        assertThat(testAuditoriaNota.getNotaAnterior()).isEqualTo(UPDATED_NOTA_ANTERIOR);
        assertThat(testAuditoriaNota.getNotaNueva()).isEqualTo(UPDATED_NOTA_NUEVA);
        assertThat(testAuditoriaNota.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testAuditoriaNota.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testAuditoriaNota.getUsuarioPortal()).isEqualTo(UPDATED_USUARIO_PORTAL);
    }

    @Test
    @Transactional
    void putNonExistingAuditoriaNota() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaNotaRepository.findAll().size();
        auditoriaNota.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditoriaNotaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditoriaNota.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditoriaNota))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditoriaNota in the database
        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuditoriaNota() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaNotaRepository.findAll().size();
        auditoriaNota.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditoriaNotaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditoriaNota))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditoriaNota in the database
        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuditoriaNota() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaNotaRepository.findAll().size();
        auditoriaNota.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditoriaNotaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditoriaNota)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuditoriaNota in the database
        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuditoriaNotaWithPatch() throws Exception {
        // Initialize the database
        auditoriaNotaRepository.saveAndFlush(auditoriaNota);

        int databaseSizeBeforeUpdate = auditoriaNotaRepository.findAll().size();

        // Update the auditoriaNota using partial update
        AuditoriaNota partialUpdatedAuditoriaNota = new AuditoriaNota();
        partialUpdatedAuditoriaNota.setId(auditoriaNota.getId());

        partialUpdatedAuditoriaNota
            .auditoriaId(UPDATED_AUDITORIA_ID)
            .notaAnterior(UPDATED_NOTA_ANTERIOR)
            .notaNueva(UPDATED_NOTA_NUEVA)
            .ip(UPDATED_IP);

        restAuditoriaNotaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditoriaNota.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditoriaNota))
            )
            .andExpect(status().isOk());

        // Validate the AuditoriaNota in the database
        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeUpdate);
        AuditoriaNota testAuditoriaNota = auditoriaNotaList.get(auditoriaNotaList.size() - 1);
        assertThat(testAuditoriaNota.getAuditoriaId()).isEqualTo(UPDATED_AUDITORIA_ID);
        assertThat(testAuditoriaNota.getNotaAnterior()).isEqualTo(UPDATED_NOTA_ANTERIOR);
        assertThat(testAuditoriaNota.getNotaNueva()).isEqualTo(UPDATED_NOTA_NUEVA);
        assertThat(testAuditoriaNota.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testAuditoriaNota.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testAuditoriaNota.getUsuarioPortal()).isEqualTo(DEFAULT_USUARIO_PORTAL);
    }

    @Test
    @Transactional
    void fullUpdateAuditoriaNotaWithPatch() throws Exception {
        // Initialize the database
        auditoriaNotaRepository.saveAndFlush(auditoriaNota);

        int databaseSizeBeforeUpdate = auditoriaNotaRepository.findAll().size();

        // Update the auditoriaNota using partial update
        AuditoriaNota partialUpdatedAuditoriaNota = new AuditoriaNota();
        partialUpdatedAuditoriaNota.setId(auditoriaNota.getId());

        partialUpdatedAuditoriaNota
            .auditoriaId(UPDATED_AUDITORIA_ID)
            .notaAnterior(UPDATED_NOTA_ANTERIOR)
            .notaNueva(UPDATED_NOTA_NUEVA)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .ip(UPDATED_IP)
            .usuarioPortal(UPDATED_USUARIO_PORTAL);

        restAuditoriaNotaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditoriaNota.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditoriaNota))
            )
            .andExpect(status().isOk());

        // Validate the AuditoriaNota in the database
        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeUpdate);
        AuditoriaNota testAuditoriaNota = auditoriaNotaList.get(auditoriaNotaList.size() - 1);
        assertThat(testAuditoriaNota.getAuditoriaId()).isEqualTo(UPDATED_AUDITORIA_ID);
        assertThat(testAuditoriaNota.getNotaAnterior()).isEqualTo(UPDATED_NOTA_ANTERIOR);
        assertThat(testAuditoriaNota.getNotaNueva()).isEqualTo(UPDATED_NOTA_NUEVA);
        assertThat(testAuditoriaNota.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testAuditoriaNota.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testAuditoriaNota.getUsuarioPortal()).isEqualTo(UPDATED_USUARIO_PORTAL);
    }

    @Test
    @Transactional
    void patchNonExistingAuditoriaNota() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaNotaRepository.findAll().size();
        auditoriaNota.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditoriaNotaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, auditoriaNota.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditoriaNota))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditoriaNota in the database
        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuditoriaNota() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaNotaRepository.findAll().size();
        auditoriaNota.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditoriaNotaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditoriaNota))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditoriaNota in the database
        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuditoriaNota() throws Exception {
        int databaseSizeBeforeUpdate = auditoriaNotaRepository.findAll().size();
        auditoriaNota.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditoriaNotaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(auditoriaNota))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuditoriaNota in the database
        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuditoriaNota() throws Exception {
        // Initialize the database
        auditoriaNotaRepository.saveAndFlush(auditoriaNota);

        int databaseSizeBeforeDelete = auditoriaNotaRepository.findAll().size();

        // Delete the auditoriaNota
        restAuditoriaNotaMockMvc
            .perform(delete(ENTITY_API_URL_ID, auditoriaNota.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuditoriaNota> auditoriaNotaList = auditoriaNotaRepository.findAll();
        assertThat(auditoriaNotaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
