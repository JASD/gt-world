/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "asistencia_evento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsistenciaEvento.findAll", query = "SELECT a FROM AsistenciaEvento a"),
    @NamedQuery(name = "AsistenciaEvento.findByIdUsuario", query = "SELECT a FROM AsistenciaEvento a WHERE a.asistenciaEventoPK.idUsuario = :idUsuario"),
    @NamedQuery(name = "AsistenciaEvento.findByIdEvento", query = "SELECT a FROM AsistenciaEvento a WHERE a.asistenciaEventoPK.idEvento = :idEvento"),
    @NamedQuery(name = "AsistenciaEvento.findByIdPoi", query = "SELECT a FROM AsistenciaEvento a WHERE a.asistenciaEventoPK.idPoi = :idPoi"),
    @NamedQuery(name = "AsistenciaEvento.findByFechaAsistenciaEvento", query = "SELECT a FROM AsistenciaEvento a WHERE a.fechaAsistenciaEvento = :fechaAsistenciaEvento"),
    @NamedQuery(name = "AsistenciaEvento.findByComentarioAsistenciaEvento", query = "SELECT a FROM AsistenciaEvento a WHERE a.comentarioAsistenciaEvento = :comentarioAsistenciaEvento"),
    @NamedQuery(name = "AsistenciaEvento.findByLastFechaEvento", query = "SELECT a FROM AsistenciaEvento a WHERE a.asistenciaEventoPK.idUsuario = :idUsuario ORDER BY a.fechaAsistenciaEvento DESC")})
public class AsistenciaEvento implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AsistenciaEventoPK asistenciaEventoPK;
    @Column(name = "FECHA_ASISTENCIA_EVENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsistenciaEvento;
    @Size(max = 500)
    @Column(name = "COMENTARIO_ASISTENCIA_EVENTO")
    private String comentarioAsistenciaEvento;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumns({
        @JoinColumn(name = "ID_EVENTO", referencedColumnName = "ID_EVENTO", insertable = false, updatable = false),
        @JoinColumn(name = "ID_POI", referencedColumnName = "ID_POI", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private EventoPoi eventoPoi;

    public AsistenciaEvento() {
    }

    public AsistenciaEvento(AsistenciaEventoPK asistenciaEventoPK) {
        this.asistenciaEventoPK = asistenciaEventoPK;
    }

    public AsistenciaEvento(String idUsuario, long idEvento, long idPoi) {
        this.asistenciaEventoPK = new AsistenciaEventoPK(idUsuario, idEvento, idPoi);
    }

    public AsistenciaEventoPK getAsistenciaEventoPK() {
        return asistenciaEventoPK;
    }

    public void setAsistenciaEventoPK(AsistenciaEventoPK asistenciaEventoPK) {
        this.asistenciaEventoPK = asistenciaEventoPK;
    }

    public Date getFechaAsistenciaEvento() {
        return fechaAsistenciaEvento;
    }

    public void setFechaAsistenciaEvento(Date fechaAsistenciaEvento) {
        this.fechaAsistenciaEvento = fechaAsistenciaEvento;
    }

    public String getComentarioAsistenciaEvento() {
        return comentarioAsistenciaEvento;
    }

    public void setComentarioAsistenciaEvento(String comentarioAsistenciaEvento) {
        this.comentarioAsistenciaEvento = comentarioAsistenciaEvento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EventoPoi getEventoPoi() {
        return eventoPoi;
    }

    public void setEventoPoi(EventoPoi eventoPoi) {
        this.eventoPoi = eventoPoi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (asistenciaEventoPK != null ? asistenciaEventoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistenciaEvento)) {
            return false;
        }
        AsistenciaEvento other = (AsistenciaEvento) object;
        if ((this.asistenciaEventoPK == null && other.asistenciaEventoPK != null) || (this.asistenciaEventoPK != null && !this.asistenciaEventoPK.equals(other.asistenciaEventoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.AsistenciaEvento[ asistenciaEventoPK=" + asistenciaEventoPK + " ]";
    }
}
