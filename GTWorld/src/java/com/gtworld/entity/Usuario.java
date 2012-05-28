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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByNombreUsuario", query = "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "Usuario.findByApellidoUsuario", query = "SELECT u FROM Usuario u WHERE u.apellidoUsuario = :apellidoUsuario"),
    @NamedQuery(name = "Usuario.findByEmailUsuario", query = "SELECT u FROM Usuario u WHERE u.emailUsuario = :emailUsuario"),
    @NamedQuery(name = "Usuario.findByTipoUsuario", query = "SELECT u FROM Usuario u WHERE u.tipoUsuario = :tipoUsuario"),
    @NamedQuery(name = "Usuario.findByContrasenaUsuario", query = "SELECT u FROM Usuario u WHERE u.contrasenaUsuario = :contrasenaUsuario"),
    @NamedQuery(name = "Usuario.findByFechaIngresoUsuario", query = "SELECT u FROM Usuario u WHERE u.fechaIngresoUsuario = :fechaIngresoUsuario"),
    @NamedQuery(name = "Usuario.findByLogin", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario AND u.contrasenaUsuario = :contrasenaUsuario")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ID_USUARIO")
    private String idUsuario;
    @Size(max = 50)
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Size(max = 50)
    @Column(name = "APELLIDO_USUARIO")
    private String apellidoUsuario;
    @Size(max = 50)
    @Column(name = "EMAIL_USUARIO")
    private String emailUsuario;
    @Column(name = "TIPO_USUARIO")
    private Boolean tipoUsuario;
    @Size(max = 15)
    @Column(name = "CONTRASENA_USUARIO")
    private String contrasenaUsuario;
    @Column(name = "FECHA_INGRESO_USUARIO")
    @Temporal(TemporalType.DATE)
    private Date fechaIngresoUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Poi> poiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<VisitaUbicacion> visitaUbicacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Red> redList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<VisitaPoi> visitaPoiList;
    @JoinColumn(name = "CORRELATIVO_IMAGEN", referencedColumnName = "CORRELATIVO_IMAGEN")
    @ManyToOne
    private Imagen correlativoImagen;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<Notificacion> notificacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Miembro> miembroList;

    public Usuario() {
    }

    public Usuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public Boolean getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Boolean tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getContrasenaUsuario() {
        return contrasenaUsuario;
    }

    public void setContrasenaUsuario(String contrasenaUsuario) {
        this.contrasenaUsuario = contrasenaUsuario;
    }

    public Date getFechaIngresoUsuario() {
        return fechaIngresoUsuario;
    }

    public void setFechaIngresoUsuario(Date fechaIngresoUsuario) {
        this.fechaIngresoUsuario = fechaIngresoUsuario;
    }

    @XmlTransient
    public List<Poi> getPoiList() {
        return poiList;
    }

    public void setPoiList(List<Poi> poiList) {
        this.poiList = poiList;
    }

    @XmlTransient
    public List<VisitaUbicacion> getVisitaUbicacionList() {
        return visitaUbicacionList;
    }

    public void setVisitaUbicacionList(List<VisitaUbicacion> visitaUbicacionList) {
        this.visitaUbicacionList = visitaUbicacionList;
    }

    @XmlTransient
    public List<Red> getRedList() {
        return redList;
    }

    public void setRedList(List<Red> redList) {
        this.redList = redList;
    }

    @XmlTransient
    public List<VisitaPoi> getVisitaPoiList() {
        return visitaPoiList;
    }

    public void setVisitaPoiList(List<VisitaPoi> visitaPoiList) {
        this.visitaPoiList = visitaPoiList;
    }

    public Imagen getCorrelativoImagen() {
        return correlativoImagen;
    }

    public void setCorrelativoImagen(Imagen correlativoImagen) {
        this.correlativoImagen = correlativoImagen;
    }

    @XmlTransient
    public List<Notificacion> getNotificacionList() {
        return notificacionList;
    }

    public void setNotificacionList(List<Notificacion> notificacionList) {
        this.notificacionList = notificacionList;
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
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.Usuario[ idUsuario=" + idUsuario + " ]";
    }
}
