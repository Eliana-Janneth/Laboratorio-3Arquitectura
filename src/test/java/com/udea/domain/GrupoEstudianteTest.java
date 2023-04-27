package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GrupoEstudianteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoEstudiante.class);
        GrupoEstudiante grupoEstudiante1 = new GrupoEstudiante();
        grupoEstudiante1.setId(1L);
        GrupoEstudiante grupoEstudiante2 = new GrupoEstudiante();
        grupoEstudiante2.setId(grupoEstudiante1.getId());
        assertThat(grupoEstudiante1).isEqualTo(grupoEstudiante2);
        grupoEstudiante2.setId(2L);
        assertThat(grupoEstudiante1).isNotEqualTo(grupoEstudiante2);
        grupoEstudiante1.setId(null);
        assertThat(grupoEstudiante1).isNotEqualTo(grupoEstudiante2);
    }
}
