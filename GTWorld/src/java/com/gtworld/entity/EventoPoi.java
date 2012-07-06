/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "evento_poi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventoPoi.findAll", query = "SELECT e FROM EventoPoi e"),
    @NamedQuery(name = "EventoPoi.findByIdEvento", query = "SELECT e FROM EventoPoi e WHERE e.eventoPoiPK.idEvento = :idEvento"),
    @NamedQuery(name = "EventoPoi.findByIdPoi", query = "SELECT e FROM EventoPoi e WHERE e.eventoPoiPK.idPoi = :idPoi")})
public class EventoPoi implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EventoPoiPK eventoPoiPK;
    @JoinColumn(name = "ID_POI", referencedColumnName = "ID_POI", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Poi poi;
    @JoinColumn(name = "ID_EVENTO", referencedColumnName = "ID_EVENTO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Evento evento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventoPoi")
    private List<AsistenciaEvento> asistenciaEventoList;

    public EventoPoi() {
    }

    public EventoPoi(EventoPoiPK eventoPoiPK) {
        this.eventoPoiPK = eventoPoiPK;
    }

    public EventoPoi(long idEvento, long idPoi) {
        this.eventoPoiPK = new EventoPoiPK(idEvento, idPoi);
    }

    public EventoPoiPK getEventoPoiPK() {
        return eventoPoiPK;
    }

    public void setEventoPoiPK(EventoPoiPK eventoPoiPK) {
        this.eventoPoiPK = eventoPoiPK;
    }

    public Poi getPoi() {
        return poi;
    }

    public void setPoi(Poi poi) {
        this.poi = poi;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    @XmlTransient
    public List<AsistenciaEvento> getAsistenciaEventoList() {
        return asistenciaEventoList;
    }

    public void setAsistenciaEventoList(List<AsistenciaEvento> asistenciaEventoList) {
        this.asistenciaEventoList = asistenciaEventoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventoPoiPK != null ? eventoPoiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventoPoi)) {
            return false;
        }
        EventoPoi other = (EventoPoi) object;
        if ((this.eventoPoiPK == null && other.eventoPoiPK != null) || (this.eventoPoiPK != null && !this.eventoPoiPK.equals(other.eventoPoiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.EventoPoi[ eventoPoiPK=" + eventoPoiPK + " ]";
    }
    
}
