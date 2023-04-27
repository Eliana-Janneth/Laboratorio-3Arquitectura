package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Actividad.
 */
@Entity
@Table(name = "actividad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Actividad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "actividad_id", nullable = false)
    private Integer actividadId;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "porcentaje", nullable = false)
    private Float porcentaje;

    @OneToMany(mappedBy = "actividad")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "auditoriasNotas", "actividad", "grupoEstudiante" }, allowSetters = true)
    private Set<Nota> notas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "estudiantes", "actividades", "curso", "semestreAcademico", "profesors" }, allowSetters = true)
    private Grupo grupo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Actividad id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActividadId() {
        return this.actividadId;
    }

    public Actividad actividadId(Integer actividadId) {
        this.setActividadId(actividadId);
        return this;
    }

    public void setActividadId(Integer actividadId) {
        this.actividadId = actividadId;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Actividad descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPorcentaje() {
        return this.porcentaje;
    }

    public Actividad porcentaje(Float porcentaje) {
        this.setPorcentaje(porcentaje);
        return this;
    }

    public void setPorcentaje(Float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Set<Nota> getNotas() {
        return this.notas;
    }

    public void setNotas(Set<Nota> notas) {
        if (this.notas != null) {
            this.notas.forEach(i -> i.setActividad(null));
        }
        if (notas != null) {
            notas.forEach(i -> i.setActividad(this));
        }
        this.notas = notas;
    }

    public Actividad notas(Set<Nota> notas) {
        this.setNotas(notas);
        return this;
    }

    public Actividad addNotas(Nota nota) {
        this.notas.add(nota);
        nota.setActividad(this);
        return this;
    }

    public Actividad removeNotas(Nota nota) {
        this.notas.remove(nota);
        nota.setActividad(null);
        return this;
    }

    public Grupo getGrupo() {
        return this.grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Actividad grupo(Grupo grupo) {
        this.setGrupo(grupo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Actividad)) {
            return false;
        }
        return id != null && id.equals(((Actividad) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Actividad{" +
            "id=" + getId() +
            ", actividadId=" + getActividadId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", porcentaje=" + getPorcentaje() +
            "}";
    }
}
