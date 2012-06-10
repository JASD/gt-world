/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Antonio
 */
@Embeddable
public class VisitaUbicacionPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UBICACION")
    private long idUbicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ID_USUARIO")
    private String idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_VISITA_UBICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVisitaUbicacion;

    public VisitaUbicacionPK() {
    }

    public VisitaUbicacionPK(long idUbicacion, String idUsuario, Date fechaVisitaUbicacion) {
        this.idUbicacion = idUbicacion;
        this.idUsuario = idUsuario;
        this.fechaVisitaUbicacion = fechaVisitaUbicacion;
    }

    public long getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaVisitaUbicacion() {
        return fechaVisitaUbicacion;
    }

    public void setFechaVisitaUbicacion(Date fechaVisitaUbicacion) {
        this.fechaVisitaUbicacion = fechaVisitaUbicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idUbicacion;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        hash += (fechaVisitaUbicacion != null ? fechaVisitaUbicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisitaUbicacionPK)) {
            return false;
        }
        VisitaUbicacionPK other = (VisitaUbicacionPK) object;
        if (this.idUbicacion != other.idUbicacion) {
            return false;
        }
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        if ((this.fechaVisitaUbicacion == null && other.fechaVisitaUbicacion != null) || (this.fechaVisitaUbicacion != null && !this.fechaVisitaUbicacion.equals(other.fechaVisitaUbicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.VisitaUbicacionPK[ idUbicacion=" + idUbicacion + ", idUsuario=" + idUsuario + ", fechaVisitaUbicacion=" + fechaVisitaUbicacion + " ]";
    }
    
}
