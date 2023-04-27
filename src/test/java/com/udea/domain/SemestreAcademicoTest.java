package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SemestreAcademicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SemestreAcademico.class);
        SemestreAcademico semestreAcademico1 = new SemestreAcademico();
        semestreAcademico1.setId(1L);
        SemestreAcademico semestreAcademico2 = new SemestreAcademico();
        semestreAcademico2.setId(semestreAcademico1.getId());
        assertThat(semestreAcademico1).isEqualTo(semestreAcademico2);
        semestreAcademico2.setId(2L);
        assertThat(semestreAcademico1).isNotEqualTo(semestreAcademico2);
        semestreAcademico1.setId(null);
        assertThat(semestreAcademico1).isNotEqualTo(semestreAcademico2);
    }
}
