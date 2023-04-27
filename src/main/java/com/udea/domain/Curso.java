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
 * A Curso.
 */
@Entity
@Table(name = "curso")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "curso_id", nullable = false)
    private Integer cursoId;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "credito", nullable = false)
    private Integer credito;

    @OneToMany(mappedBy = "curso")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estudiantes", "actividades", "curso", "semestreAcademico", "profesors" }, allowSetters = true)
    private Set<Grupo> grupos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Curso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCursoId() {
        return this.cursoId;
    }

    public Curso cursoId(Integer cursoId) {
        this.setCursoId(cursoId);
        return this;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Curso codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Curso nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCredito() {
        return this.credito;
    }

    public Curso credito(Integer credito) {
        this.setCredito(credito);
        return this;
    }

    public void setCredito(Integer credito) {
        this.credito = credito;
    }

    public Set<Grupo> getGrupos() {
        return this.grupos;
    }

    public void setGrupos(Set<Grupo> grupos) {
        if (this.grupos != null) {
            this.grupos.forEach(i -> i.setCurso(null));
        }
        if (grupos != null) {
            grupos.forEach(i -> i.setCurso(this));
        }
        this.grupos = grupos;
    }

    public Curso grupos(Set<Grupo> grupos) {
        this.setGrupos(grupos);
        return this;
    }

    public Curso addGrupos(Grupo grupo) {
        this.grupos.add(grupo);
        grupo.setCurso(this);
        return this;
    }

    public Curso removeGrupos(Grupo grupo) {
        this.grupos.remove(grupo);
        grupo.setCurso(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Curso)) {
            return false;
        }
        return id != null && id.equals(((Curso) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Curso{" +
            "id=" + getId() +
            ", cursoId=" + getCursoId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", credito=" + getCredito() +
            "}";
    }
}
