/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "visita_ubicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VisitaUbicacion.findAll", query = "SELECT v FROM VisitaUbicacion v"),
    @NamedQuery(name = "VisitaUbicacion.findByIdUbicacion", query = "SELECT v FROM VisitaUbicacion v WHERE v.visitaUbicacionPK.idUbicacion = :idUbicacion"),
    @NamedQuery(name = "VisitaUbicacion.findByIdUsuario", query = "SELECT v FROM VisitaUbicacion v WHERE v.visitaUbicacionPK.idUsuario = :idUsuario"),
    @NamedQuery(name = "VisitaUbicacion.findByFechaVisitaUbicacion", query = "SELECT v FROM VisitaUbicacion v WHERE v.visitaUbicacionPK.fechaVisitaUbicacion = :fechaVisitaUbicacion"),
    @NamedQuery(name = "VisitaUbicacion.findByHoraVisitaUbicacion", query = "SELECT v FROM VisitaUbicacion v WHERE v.visitaUbicacionPK.horaVisitaUbicacion = :horaVisitaUbicacion")})
public class VisitaUbicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VisitaUbicacionPK visitaUbicacionPK;
    @JoinColumn(name = "ID_UBICACION", referencedColumnName = "ID_UBICACION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ubicacion ubicacion;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public VisitaUbicacion() {
    }

    public VisitaUbicacion(VisitaUbicacionPK visitaUbicacionPK) {
        this.visitaUbicacionPK = visitaUbicacionPK;
    }

    public VisitaUbicacion(long idUbicacion, String idUsuario, Date fechaVisitaUbicacion, Date horaVisitaUbicacion) {
        this.visitaUbicacionPK = new VisitaUbicacionPK(idUbicacion, idUsuario, fechaVisitaUbicacion, horaVisitaUbicacion);
    }

    public VisitaUbicacionPK getVisitaUbicacionPK() {
        return visitaUbicacionPK;
    }

    public void setVisitaUbicacionPK(VisitaUbicacionPK visitaUbicacionPK) {
        this.visitaUbicacionPK = visitaUbicacionPK;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visitaUbicacionPK != null ? visitaUbicacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisitaUbicacion)) {
            return false;
        }
        VisitaUbicacion other = (VisitaUbicacion) object;
        if ((this.visitaUbicacionPK == null && other.visitaUbicacionPK != null) || (this.visitaUbicacionPK != null && !this.visitaUbicacionPK.equals(other.visitaUbicacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.VisitaUbicacion[ visitaUbicacionPK=" + visitaUbicacionPK + " ]";
    }
    
}
