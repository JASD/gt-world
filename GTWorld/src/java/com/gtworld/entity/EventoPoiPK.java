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

/**
 *
 * @author Antonio
 */
@Embeddable
public class EventoPoiPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_EVENTO")
    private long idEvento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_POI")
    private long idPoi;

    public EventoPoiPK() {
    }

    public EventoPoiPK(long idEvento, long idPoi) {
        this.idEvento = idEvento;
        this.idPoi = idPoi;
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
        hash += (int) idEvento;
        hash += (int) idPoi;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventoPoiPK)) {
            return false;
        }
        EventoPoiPK other = (EventoPoiPK) object;
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
        return "com.gtworld.entity.EventoPoiPK[ idEvento=" + idEvento + ", idPoi=" + idPoi + " ]";
    }
    
}
