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
 * A Profesor.
 */
@Entity
@Table(name = "profesor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profesor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "profesor_id", nullable = false)
    private Integer profesorId;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "correo_electronico", nullable = false)
    private String correoElectronico;

    @NotNull
    @Column(name = "usuario_portal", nullable = false)
    private String usuarioPortal;

    @OneToMany(mappedBy = "profesor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "profesor", "nota" }, allowSetters = true)
    private Set<AuditoriaNota> auditoriasNotas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "estudiantes", "actividades", "curso", "semestreAcademico", "profesors" }, allowSetters = true)
    private Grupo grupos;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Profesor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProfesorId() {
        return this.profesorId;
    }

    public Profesor profesorId(Integer profesorId) {
        this.setProfesorId(profesorId);
        return this;
    }

    public void setProfesorId(Integer profesorId) {
        this.profesorId = profesorId;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Profesor nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Profesor tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    public Profesor correoElectronico(String correoElectronico) {
        this.setCorreoElectronico(correoElectronico);
        return this;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getUsuarioPortal() {
        return this.usuarioPortal;
    }

    public Profesor usuarioPortal(String usuarioPortal) {
        this.setUsuarioPortal(usuarioPortal);
        return this;
    }

    public void setUsuarioPortal(String usuarioPortal) {
        this.usuarioPortal = usuarioPortal;
    }

    public Set<AuditoriaNota> getAuditoriasNotas() {
        return this.auditoriasNotas;
    }

    public void setAuditoriasNotas(Set<AuditoriaNota> auditoriaNotas) {
        if (this.auditoriasNotas != null) {
            this.auditoriasNotas.forEach(i -> i.setProfesor(null));
        }
        if (auditoriaNotas != null) {
            auditoriaNotas.forEach(i -> i.setProfesor(this));
        }
        this.auditoriasNotas = auditoriaNotas;
    }

    public Profesor auditoriasNotas(Set<AuditoriaNota> auditoriaNotas) {
        this.setAuditoriasNotas(auditoriaNotas);
        return this;
    }

    public Profesor addAuditoriasNota(AuditoriaNota auditoriaNota) {
        this.auditoriasNotas.add(auditoriaNota);
        auditoriaNota.setProfesor(this);
        return this;
    }

    public Profesor removeAuditoriasNota(AuditoriaNota auditoriaNota) {
        this.auditoriasNotas.remove(auditoriaNota);
        auditoriaNota.setProfesor(null);
        return this;
    }

    public Grupo getGrupos() {
        return this.grupos;
    }

    public void setGrupos(Grupo grupo) {
        this.grupos = grupo;
    }

    public Profesor grupos(Grupo grupo) {
        this.setGrupos(grupo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profesor)) {
            return false;
        }
        return id != null && id.equals(((Profesor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profesor{" +
            "id=" + getId() +
            ", profesorId=" + getProfesorId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", correoElectronico='" + getCorreoElectronico() + "'" +
            ", usuarioPortal='" + getUsuarioPortal() + "'" +
            "}";
    }
}
