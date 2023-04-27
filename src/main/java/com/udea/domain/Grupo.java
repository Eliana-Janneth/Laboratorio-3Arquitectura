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
 * A Grupo.
 */
@Entity
@Table(name = "grupo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "grupo_id", nullable = false)
    private Integer grupoId;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Column(name = "horario")
    private String horario;

    @OneToMany(mappedBy = "grupo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "notas", "grupo", "estudiantes" }, allowSetters = true)
    private Set<GrupoEstudiante> estudiantes = new HashSet<>();

    @OneToMany(mappedBy = "grupo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "notas", "grupo" }, allowSetters = true)
    private Set<Actividad> actividades = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "grupos" }, allowSetters = true)
    private Curso curso;

    @ManyToOne
    @JsonIgnoreProperties(value = { "grupos" }, allowSetters = true)
    private SemestreAcademico semestreAcademico;

    @OneToMany(mappedBy = "grupos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "auditoriasNotas", "grupos" }, allowSetters = true)
    private Set<Profesor> profesors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Grupo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGrupoId() {
        return this.grupoId;
    }

    public Grupo grupoId(Integer grupoId) {
        this.setGrupoId(grupoId);
        return this;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Grupo codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getHorario() {
        return this.horario;
    }

    public Grupo horario(String horario) {
        this.setHorario(horario);
        return this;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Set<GrupoEstudiante> getEstudiantes() {
        return this.estudiantes;
    }

    public void setEstudiantes(Set<GrupoEstudiante> grupoEstudiantes) {
        if (this.estudiantes != null) {
            this.estudiantes.forEach(i -> i.setGrupo(null));
        }
        if (grupoEstudiantes != null) {
            grupoEstudiantes.forEach(i -> i.setGrupo(this));
        }
        this.estudiantes = grupoEstudiantes;
    }

    public Grupo estudiantes(Set<GrupoEstudiante> grupoEstudiantes) {
        this.setEstudiantes(grupoEstudiantes);
        return this;
    }

    public Grupo addEstudiantes(GrupoEstudiante grupoEstudiante) {
        this.estudiantes.add(grupoEstudiante);
        grupoEstudiante.setGrupo(this);
        return this;
    }

    public Grupo removeEstudiantes(GrupoEstudiante grupoEstudiante) {
        this.estudiantes.remove(grupoEstudiante);
        grupoEstudiante.setGrupo(null);
        return this;
    }

    public Set<Actividad> getActividades() {
        return this.actividades;
    }

    public void setActividades(Set<Actividad> actividads) {
        if (this.actividades != null) {
            this.actividades.forEach(i -> i.setGrupo(null));
        }
        if (actividads != null) {
            actividads.forEach(i -> i.setGrupo(this));
        }
        this.actividades = actividads;
    }

    public Grupo actividades(Set<Actividad> actividads) {
        this.setActividades(actividads);
        return this;
    }

    public Grupo addActividades(Actividad actividad) {
        this.actividades.add(actividad);
        actividad.setGrupo(this);
        return this;
    }

    public Grupo removeActividades(Actividad actividad) {
        this.actividades.remove(actividad);
        actividad.setGrupo(null);
        return this;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Grupo curso(Curso curso) {
        this.setCurso(curso);
        return this;
    }

    public SemestreAcademico getSemestreAcademico() {
        return this.semestreAcademico;
    }

    public void setSemestreAcademico(SemestreAcademico semestreAcademico) {
        this.semestreAcademico = semestreAcademico;
    }

    public Grupo semestreAcademico(SemestreAcademico semestreAcademico) {
        this.setSemestreAcademico(semestreAcademico);
        return this;
    }

    public Set<Profesor> getProfesors() {
        return this.profesors;
    }

    public void setProfesors(Set<Profesor> profesors) {
        if (this.profesors != null) {
            this.profesors.forEach(i -> i.setGrupos(null));
        }
        if (profesors != null) {
            profesors.forEach(i -> i.setGrupos(this));
        }
        this.profesors = profesors;
    }

    public Grupo profesors(Set<Profesor> profesors) {
        this.setProfesors(profesors);
        return this;
    }

    public Grupo addProfesor(Profesor profesor) {
        this.profesors.add(profesor);
        profesor.setGrupos(this);
        return this;
    }

    public Grupo removeProfesor(Profesor profesor) {
        this.profesors.remove(profesor);
        profesor.setGrupos(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grupo)) {
            return false;
        }
        return id != null && id.equals(((Grupo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Grupo{" +
            "id=" + getId() +
            ", grupoId=" + getGrupoId() +
            ", codigo='" + getCodigo() + "'" +
            ", horario='" + getHorario() + "'" +
            "}";
    }
}
