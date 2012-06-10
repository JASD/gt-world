/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "red")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Red.findAll", query = "SELECT r FROM Red r"),
    @NamedQuery(name = "Red.findByIdRed", query = "SELECT r FROM Red r WHERE r.idRed = :idRed"),
    @NamedQuery(name = "Red.findByNombreRed", query = "SELECT r FROM Red r WHERE r.nombreRed = :nombreRed"),
    @NamedQuery(name = "Red.findByDescripcionRed", query = "SELECT r FROM Red r WHERE r.descripcionRed = :descripcionRed"),
    @NamedQuery(name = "Red.findByFechaCreacionRed", query = "SELECT r FROM Red r WHERE r.fechaCreacionRed = :fechaCreacionRed")})
public class Red implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_RED")
    private Long idRed;
    @Size(max = 50)
    @Column(name = "NOMBRE_RED")
    private String nombreRed;
    @Size(max = 250)
    @Column(name = "DESCRIPCION_RED")
    private String descripcionRed;
    @Column(name = "FECHA_CREACION_RED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacionRed;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "red")
    private List<Miembro> miembroList;

    public Red() {
    }

    public Red(Long idRed) {
        this.idRed = idRed;
    }

    public Long getIdRed() {
        return idRed;
    }

    public void setIdRed(Long idRed) {
        this.idRed = idRed;
    }

    public String getNombreRed() {
        return nombreRed;
    }

    public void setNombreRed(String nombreRed) {
        this.nombreRed = nombreRed;
    }

    public String getDescripcionRed() {
        return descripcionRed;
    }

    public void setDescripcionRed(String descripcionRed) {
        this.descripcionRed = descripcionRed;
    }

    public Date getFechaCreacionRed() {
        return fechaCreacionRed;
    }

    public void setFechaCreacionRed(Date fechaCreacionRed) {
        this.fechaCreacionRed = fechaCreacionRed;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<Miembro> getMiembroList() {
        return miembroList;
    }

    public void setMiembroList(List<Miembro> miembroList) {
        this.miembroList = miembroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRed != null ? idRed.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Red)) {
            return false;
        }
        Red other = (Red) object;
        if ((this.idRed == null && other.idRed != null) || (this.idRed != null && !this.idRed.equals(other.idRed))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.Red[ idRed=" + idRed + " ]";
    }
    
}
