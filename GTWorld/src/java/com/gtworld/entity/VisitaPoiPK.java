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
public class VisitaPoiPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ID_USUARIO")
    private String idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_POI")
    private long idPoi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_VISITA_POI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVisitaPoi;

    public VisitaPoiPK() {
    }

    public VisitaPoiPK(String idUsuario, long idPoi, Date fechaVisitaPoi) {
        this.idUsuario = idUsuario;
        this.idPoi = idPoi;
        this.fechaVisitaPoi = fechaVisitaPoi;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdPoi() {
        return idPoi;
    }

    public void setIdPoi(long idPoi) {
        this.idPoi = idPoi;
    }

    public Date getFechaVisitaPoi() {
        return fechaVisitaPoi;
    }

    public void setFechaVisitaPoi(Date fechaVisitaPoi) {
        this.fechaVisitaPoi = fechaVisitaPoi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        hash += (int) idPoi;
        hash += (fechaVisitaPoi != null ? fechaVisitaPoi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisitaPoiPK)) {
            return false;
        }
        VisitaPoiPK other = (VisitaPoiPK) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        if (this.idPoi != other.idPoi) {
            return false;
        }
        if ((this.fechaVisitaPoi == null && other.fechaVisitaPoi != null) || (this.fechaVisitaPoi != null && !this.fechaVisitaPoi.equals(other.fechaVisitaPoi))) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.VisitaPoiPK[ idUsuario=" + idUsuario + ", idPoi=" + idPoi + ", fechaVisitaPoi=" + fechaVisitaPoi + " ]";
    }
    
}
