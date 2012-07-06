/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Antonio
 */
@Embeddable
public class AsistenciaEventoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ID_USUARIO")
    private String idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_EVENTO")
    private long idEvento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_POI")
    private long idPoi;

    public AsistenciaEventoPK() {
    }

    public AsistenciaEventoPK(String idUsuario, long idEvento, long idPoi) {
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
        this.idPoi = idPoi;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(long idEvento) {
        this.idEvento = idEvento;
    }

    public long getIdPoi() {
        return idPoi;
    }

    public void setIdPoi(long idPoi) {
        this.idPoi = idPoi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        hash += (int) idEvento;
        hash += (int) idPoi;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistenciaEventoPK)) {
            return false;
        }
        AsistenciaEventoPK other = (AsistenciaEventoPK) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        if (this.idEvento != other.idEvento) {
            return false;
        }
        if (this.idPoi != other.idPoi) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.AsistenciaEventoPK[ idUsuario=" + idUsuario + ", idEvento=" + idEvento + ", idPoi=" + idPoi + " ]";
    }
    
}
