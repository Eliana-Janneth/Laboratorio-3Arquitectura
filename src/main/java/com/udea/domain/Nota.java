package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Nota.
 */
@Entity
@Table(name = "nota")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nota implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nota_id", nullable = false)
    private Integer notaId;

    @NotNull
    @Column(name = "nota", nullable = false)
    private Float nota;

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @OneToMany(mappedBy = "nota")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "profesor", "nota" }, allowSetters = true)
    private Set<AuditoriaNota> auditoriasNotas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "notas", "grupo" }, allowSetters = true)
    private Actividad actividad;

    @ManyToOne
    @JsonIgnoreProperties(value = { "notas", "grupo", "estudiantes" }, allowSetters = true)
    private GrupoEstudiante grupoEstudiante;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nota id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNotaId() {
        return this.notaId;
    }

    public Nota notaId(Integer notaId) {
        this.setNotaId(notaId);
        return this;
    }

    public void setNotaId(Integer notaId) {
        this.notaId = notaId;
    }

    public Float getNota() {
        return this.nota;
    }

    public Nota nota(Float nota) {
        this.setNota(nota);
        return this;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    public Nota fechaCreacion(LocalDate fechaCreacion) {
        this.setFechaCreacion(fechaCreacion);
        return this;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Set<AuditoriaNota> getAuditoriasNotas() {
        return this.auditoriasNotas;
    }

    public void setAuditoriasNotas(Set<AuditoriaNota> auditoriaNotas) {
        if (this.auditoriasNotas != null) {
            this.auditoriasNotas.forEach(i -> i.setNota(null));
        }
        if (auditoriaNotas != null) {
            auditoriaNotas.forEach(i -> i.setNota(this));
        }
        this.auditoriasNotas = auditoriaNotas;
    }

    public Nota auditoriasNotas(Set<AuditoriaNota> auditoriaNotas) {
        this.setAuditoriasNotas(auditoriaNotas);
        return this;
    }

    public Nota addAuditoriasNota(AuditoriaNota auditoriaNota) {
        this.auditoriasNotas.add(auditoriaNota);
        auditoriaNota.setNota(this);
        return this;
    }

    public Nota removeAuditoriasNota(AuditoriaNota auditoriaNota) {
        this.auditoriasNotas.remove(auditoriaNota);
        auditoriaNota.setNota(null);
        return this;
    }

    public Actividad getActividad() {
        return this.actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Nota actividad(Actividad actividad) {
        this.setActividad(actividad);
        return this;
    }

    public GrupoEstudiante getGrupoEstudiante() {
        return this.grupoEstudiante;
    }

    public void setGrupoEstudiante(GrupoEstudiante grupoEstudiante) {
        this.grupoEstudiante = grupoEstudiante;
    }

    public Nota grupoEstudiante(GrupoEstudiante grupoEstudiante) {
        this.setGrupoEstudiante(grupoEstudiante);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nota)) {
            return false;
        }
        return id != null && id.equals(((Nota) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nota{" +
            "id=" + getId() +
            ", notaId=" + getNotaId() +
            ", nota=" + getNota() +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            "}";
    }
}
