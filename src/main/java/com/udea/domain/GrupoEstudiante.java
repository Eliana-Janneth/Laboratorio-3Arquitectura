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
 * A GrupoEstudiante.
 */
@Entity
@Table(name = "grupo_estudiante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GrupoEstudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "grupo_estudiante_id", nullable = false)
    private Integer grupoEstudianteId;

    @OneToMany(mappedBy = "grupoEstudiante")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "auditoriasNotas", "actividad", "grupoEstudiante" }, allowSetters = true)
    private Set<Nota> notas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "estudiantes", "actividades", "curso", "semestreAcademico", "profesors" }, allowSetters = true)
    private Grupo grupo;

    @OneToMany(mappedBy = "gruposEstudiante")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "gruposEstudiante" }, allowSetters = true)
    private Set<Estudiante> estudiantes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GrupoEstudiante id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGrupoEstudianteId() {
        return this.grupoEstudianteId;
    }

    public GrupoEstudiante grupoEstudianteId(Integer grupoEstudianteId) {
        this.setGrupoEstudianteId(grupoEstudianteId);
        return this;
    }

    public void setGrupoEstudianteId(Integer grupoEstudianteId) {
        this.grupoEstudianteId = grupoEstudianteId;
    }

    public Set<Nota> getNotas() {
        return this.notas;
    }

    public void setNotas(Set<Nota> notas) {
        if (this.notas != null) {
            this.notas.forEach(i -> i.setGrupoEstudiante(null));
        }
        if (notas != null) {
            notas.forEach(i -> i.setGrupoEstudiante(this));
        }
        this.notas = notas;
    }

    public GrupoEstudiante notas(Set<Nota> notas) {
        this.setNotas(notas);
        return this;
    }

    public GrupoEstudiante addNotas(Nota nota) {
        this.notas.add(nota);
        nota.setGrupoEstudiante(this);
        return this;
    }

    public GrupoEstudiante removeNotas(Nota nota) {
        this.notas.remove(nota);
        nota.setGrupoEstudiante(null);
        return this;
    }

    public Grupo getGrupo() {
        return this.grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public GrupoEstudiante grupo(Grupo grupo) {
        this.setGrupo(grupo);
        return this;
    }

    public Set<Estudiante> getEstudiantes() {
        return this.estudiantes;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        if (this.estudiantes != null) {
            this.estudiantes.forEach(i -> i.setGruposEstudiante(null));
        }
        if (estudiantes != null) {
            estudiantes.forEach(i -> i.setGruposEstudiante(this));
        }
        this.estudiantes = estudiantes;
    }

    public GrupoEstudiante estudiantes(Set<Estudiante> estudiantes) {
        this.setEstudiantes(estudiantes);
        return this;
    }

    public GrupoEstudiante addEstudiante(Estudiante estudiante) {
        this.estudiantes.add(estudiante);
        estudiante.setGruposEstudiante(this);
        return this;
    }

    public GrupoEstudiante removeEstudiante(Estudiante estudiante) {
        this.estudiantes.remove(estudiante);
        estudiante.setGruposEstudiante(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoEstudiante)) {
            return false;
        }
        return id != null && id.equals(((GrupoEstudiante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoEstudiante{" +
            "id=" + getId() +
            ", grupoEstudianteId=" + getGrupoEstudianteId() +
            "}";
    }
}
