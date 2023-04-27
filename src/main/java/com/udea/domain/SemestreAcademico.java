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
 * A SemestreAcademico.
 */
@Entity
@Table(name = "semestre_academico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SemestreAcademico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "semestre_academico_id", nullable = false)
    private Integer semestreAcademicoId;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @NotNull
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @NotNull
    @Column(name = "anio", nullable = false)
    private Integer anio;

    @NotNull
    @Column(name = "periodo_academico", nullable = false)
    private Integer periodoAcademico;

    @OneToMany(mappedBy = "semestreAcademico")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estudiantes", "actividades", "curso", "semestreAcademico", "profesors" }, allowSetters = true)
    private Set<Grupo> grupos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SemestreAcademico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSemestreAcademicoId() {
        return this.semestreAcademicoId;
    }

    public SemestreAcademico semestreAcademicoId(Integer semestreAcademicoId) {
        this.setSemestreAcademicoId(semestreAcademicoId);
        return this;
    }

    public void setSemestreAcademicoId(Integer semestreAcademicoId) {
        this.semestreAcademicoId = semestreAcademicoId;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public SemestreAcademico codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getEstado() {
        return this.estado;
    }

    public SemestreAcademico estado(Boolean estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getAnio() {
        return this.anio;
    }

    public SemestreAcademico anio(Integer anio) {
        this.setAnio(anio);
        return this;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getPeriodoAcademico() {
        return this.periodoAcademico;
    }

    public SemestreAcademico periodoAcademico(Integer periodoAcademico) {
        this.setPeriodoAcademico(periodoAcademico);
        return this;
    }

    public void setPeriodoAcademico(Integer periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }

    public Set<Grupo> getGrupos() {
        return this.grupos;
    }

    public void setGrupos(Set<Grupo> grupos) {
        if (this.grupos != null) {
            this.grupos.forEach(i -> i.setSemestreAcademico(null));
        }
        if (grupos != null) {
            grupos.forEach(i -> i.setSemestreAcademico(this));
        }
        this.grupos = grupos;
    }

    public SemestreAcademico grupos(Set<Grupo> grupos) {
        this.setGrupos(grupos);
        return this;
    }

    public SemestreAcademico addGrupos(Grupo grupo) {
        this.grupos.add(grupo);
        grupo.setSemestreAcademico(this);
        return this;
    }

    public SemestreAcademico removeGrupos(Grupo grupo) {
        this.grupos.remove(grupo);
        grupo.setSemestreAcademico(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SemestreAcademico)) {
            return false;
        }
        return id != null && id.equals(((SemestreAcademico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SemestreAcademico{" +
            "id=" + getId() +
            ", semestreAcademicoId=" + getSemestreAcademicoId() +
            ", codigo='" + getCodigo() + "'" +
            ", estado='" + getEstado() + "'" +
            ", anio=" + getAnio() +
            ", periodoAcademico=" + getPeriodoAcademico() +
            "}";
    }
}
