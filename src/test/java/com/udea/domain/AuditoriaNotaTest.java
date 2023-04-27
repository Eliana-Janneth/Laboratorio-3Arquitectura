package com.udea.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuditoriaNotaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditoriaNota.class);
        AuditoriaNota auditoriaNota1 = new AuditoriaNota();
        auditoriaNota1.setId(1L);
        AuditoriaNota auditoriaNota2 = new AuditoriaNota();
        auditoriaNota2.setId(auditoriaNota1.getId());
        assertThat(auditoriaNota1).isEqualTo(auditoriaNota2);
        auditoriaNota2.setId(2L);
        assertThat(auditoriaNota1).isNotEqualTo(auditoriaNota2);
        auditoriaNota1.setId(null);
        assertThat(auditoriaNota1).isNotEqualTo(auditoriaNota2);
    }
}
