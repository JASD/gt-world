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
    @Temporal(TemporalType.DATE)
    private Date fechaVisitaUbicacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HORA_VISITA_UBICACION")
    @Temporal(TemporalType.TIME)
    private Date horaVisitaUbicacion;

    public VisitaUbicacionPK() {
    }

    public VisitaUbicacionPK(long idUbicacion, String idUsuario, Date fechaVisitaUbicacion, Date horaVisitaUbicacion) {
        this.idUbicacion = idUbicacion;
        this.idUsuario = idUsuario;
        this.fechaVisitaUbicacion = fechaVisitaUbicacion;
        this.horaVisitaUbicacion = horaVisitaUbicacion;
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

    public Date getHoraVisitaUbicacion() {
        return horaVisitaUbicacion;
    }

    public void setHoraVisitaUbicacion(Date horaVisitaUbicacion) {
        this.horaVisitaUbicacion = horaVisitaUbicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idUbicacion;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        hash += (fechaVisitaUbicacion != null ? fechaVisitaUbicacion.hashCode() : 0);
        hash += (horaVisitaUbicacion != null ? horaVisitaUbicacion.hashCode() : 0);
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
        if ((this.horaVisitaUbicacion == null && other.horaVisitaUbicacion != null) || (this.horaVisitaUbicacion != null && !this.horaVisitaUbicacion.equals(other.horaVisitaUbicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.VisitaUbicacionPK[ idUbicacion=" + idUbicacion + ", idUsuario=" + idUsuario + ", fechaVisitaUbicacion=" + fechaVisitaUbicacion + ", horaVisitaUbicacion=" + horaVisitaUbicacion + " ]";
    }
    
}
