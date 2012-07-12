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
@Table(name = "evento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e"),
    @NamedQuery(name = "Evento.findByIdEvento", query = "SELECT e FROM Evento e WHERE e.idEvento = :idEvento"),
    @NamedQuery(name = "Evento.findByNombreEvento", query = "SELECT e FROM Evento e WHERE e.nombreEvento = :nombreEvento"),
    @NamedQuery(name = "Evento.findByFechaEvento", query = "SELECT e FROM Evento e WHERE e.fechaEvento = :fechaEvento"),
    @NamedQuery(name = "Evento.findByDescripcionEvento", query = "SELECT e FROM Evento e WHERE e.descripcionEvento = :descripcionEvento"),
    @NamedQuery(name = "Evento.findByLocalEvento", query = "SELECT e FROM Evento e WHERE e.localEvento = :localEvento"),
    @NamedQuery(name = "Evento.findByPonenteEvento", query = "SELECT e FROM Evento e WHERE e.ponenteEvento = :ponenteEvento"),
    @NamedQuery(name = "Evento.findByEsPonenciaEvento", query = "SELECT e FROM Evento e WHERE e.esPonenciaEvento = :esPonenciaEvento"),
    @NamedQuery(name = "Evento.findByPoi", query = "SELECT e FROM Evento e WHERE e.idEvento IN (SELECT m.eventoPoiPK.idEvento FROM EventoPoi m WHERE m.eventoPoiPK.idPoi = :idPoi)")})
public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EVENTO",insertable = false, nullable = false, updatable = true)
    private Long idEvento;
    @Size(max = 100)
    @Column(name = "NOMBRE_EVENTO")
    private String nombreEvento;
    @Column(name = "FECHA_EVENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEvento;
    @Size(max = 500)
    @Column(name = "DESCRIPCION_EVENTO")
    private String descripcionEvento;
    @Size(max = 100)
    @Column(name = "LOCAL_EVENTO")
    private String localEvento;
    @Size(max = 250)
    @Column(name = "PONENTE_EVENTO")
    private String ponenteEvento;
    @Column(name = "ES_PONENCIA_EVENTO")
    private Boolean esPonenciaEvento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evento")
    private List<EventoPoi> eventoPoiList;

    public Evento() {
    }

    public Evento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public String getLocalEvento() {
        return localEvento;
    }

    public void setLocalEvento(String localEvento) {
        this.localEvento = localEvento;
    }

    public String getPonenteEvento() {
        return ponenteEvento;
    }

    public void setPonenteEvento(String ponenteEvento) {
        this.ponenteEvento = ponenteEvento;
    }

    public Boolean getEsPonenciaEvento() {
        return esPonenciaEvento;
    }

    public void setEsPonenciaEvento(Boolean esPonenciaEvento) {
        this.esPonenciaEvento = esPonenciaEvento;
    }

    @XmlTransient
    public List<EventoPoi> getEventoPoiList() {
        return eventoPoiList;
    }

    public void setEventoPoiList(List<EventoPoi> eventoPoiList) {
        this.eventoPoiList = eventoPoiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.Evento[ idEvento=" + idEvento + " ]";
    }
    
}
