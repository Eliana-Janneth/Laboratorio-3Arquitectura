package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AuditoriaNota.
 */
@Entity
@Table(name = "auditoria_nota")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuditoriaNota implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "auditoria_id", nullable = false)
    private Integer auditoriaId;

    @NotNull
    @Column(name = "nota_anterior", nullable = false)
    private Float notaAnterior;

    @NotNull
    @Column(name = "nota_nueva", nullable = false)
    private Float notaNueva;

    @NotNull
    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDate fechaModificacion;

    @NotNull
    @Column(name = "ip", nullable = false)
    private String ip;

    @NotNull
    @Column(name = "usuario_portal", nullable = false)
    private String usuarioPortal;

    @ManyToOne
    @JsonIgnoreProperties(value = { "auditoriasNotas", "grupos" }, allowSetters = true)
    private Profesor profesor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "auditoriasNotas", "actividad", "grupoEstudiante" }, allowSetters = true)
    private Nota nota;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AuditoriaNota id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAuditoriaId() {
        return this.auditoriaId;
    }

    public AuditoriaNota auditoriaId(Integer auditoriaId) {
        this.setAuditoriaId(auditoriaId);
        return this;
    }

    public void setAuditoriaId(Integer auditoriaId) {
        this.auditoriaId = auditoriaId;
    }

    public Float getNotaAnterior() {
        return this.notaAnterior;
    }

    public AuditoriaNota notaAnterior(Float notaAnterior) {
        this.setNotaAnterior(notaAnterior);
        return this;
    }

    public void setNotaAnterior(Float notaAnterior) {
        this.notaAnterior = notaAnterior;
    }

    public Float getNotaNueva() {
        return this.notaNueva;
    }

    public AuditoriaNota notaNueva(Float notaNueva) {
        this.setNotaNueva(notaNueva);
        return this;
    }

    public void setNotaNueva(Float notaNueva) {
        this.notaNueva = notaNueva;
    }

    public LocalDate getFechaModificacion() {
        return this.fechaModificacion;
    }

    public AuditoriaNota fechaModificacion(LocalDate fechaModificacion) {
        this.setFechaModificacion(fechaModificacion);
        return this;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getIp() {
        return this.ip;
    }

    public AuditoriaNota ip(String ip) {
        this.setIp(ip);
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsuarioPortal() {
        return this.usuarioPortal;
    }

    public AuditoriaNota usuarioPortal(String usuarioPortal) {
        this.setUsuarioPortal(usuarioPortal);
        return this;
    }

    public void setUsuarioPortal(String usuarioPortal) {
        this.usuarioPortal = usuarioPortal;
    }

    public Profesor getProfesor() {
        return this.profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public AuditoriaNota profesor(Profesor profesor) {
        this.setProfesor(profesor);
        return this;
    }

    public Nota getNota() {
        return this.nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public AuditoriaNota nota(Nota nota) {
        this.setNota(nota);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditoriaNota)) {
            return false;
        }
        return id != null && id.equals(((AuditoriaNota) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditoriaNota{" +
            "id=" + getId() +
            ", auditoriaId=" + getAuditoriaId() +
            ", notaAnterior=" + getNotaAnterior() +
            ", notaNueva=" + getNotaNueva() +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", ip='" + getIp() + "'" +
            ", usuarioPortal='" + getUsuarioPortal() + "'" +
            "}";
    }
}
