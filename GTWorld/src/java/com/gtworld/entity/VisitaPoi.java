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
@Table(name = "visita_poi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VisitaPoi.findAll", query = "SELECT v FROM VisitaPoi v"),
    @NamedQuery(name = "VisitaPoi.findByIdUsuario", query = "SELECT v FROM VisitaPoi v WHERE v.visitaPoiPK.idUsuario = :idUsuario"),
    @NamedQuery(name = "VisitaPoi.findByIdPoi", query = "SELECT v FROM VisitaPoi v WHERE v.visitaPoiPK.idPoi = :idPoi"),
    @NamedQuery(name = "VisitaPoi.findByFechaVisitaPoi", query = "SELECT v FROM VisitaPoi v WHERE v.visitaPoiPK.fechaVisitaPoi = :fechaVisitaPoi"),
    @NamedQuery(name = "VisitaPoi.findByHoraVisitaPoi", query = "SELECT v FROM VisitaPoi v WHERE v.visitaPoiPK.horaVisitaPoi = :horaVisitaPoi"),
    @NamedQuery(name = "VisitaPoi.findByComentarioVisitaPoi", query = "SELECT v FROM VisitaPoi v WHERE v.comentarioVisitaPoi = :comentarioVisitaPoi")})
public class VisitaPoi implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VisitaPoiPK visitaPoiPK;
    @Size(max = 500)
    @Column(name = "COMENTARIO_VISITA_POI")
    private String comentarioVisitaPoi;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "ID_POI", referencedColumnName = "ID_POI", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Poi poi;

    public VisitaPoi() {
    }

    public VisitaPoi(VisitaPoiPK visitaPoiPK) {
        this.visitaPoiPK = visitaPoiPK;
    }

    public VisitaPoi(String idUsuario, long idPoi, Date fechaVisitaPoi, Date horaVisitaPoi) {
        this.visitaPoiPK = new VisitaPoiPK(idUsuario, idPoi, fechaVisitaPoi, horaVisitaPoi);
    }

    public VisitaPoiPK getVisitaPoiPK() {
        return visitaPoiPK;
    }

    public void setVisitaPoiPK(VisitaPoiPK visitaPoiPK) {
        this.visitaPoiPK = visitaPoiPK;
    }

    public String getComentarioVisitaPoi() {
        return comentarioVisitaPoi;
    }

    public void setComentarioVisitaPoi(String comentarioVisitaPoi) {
        this.comentarioVisitaPoi = comentarioVisitaPoi;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Poi getPoi() {
        return poi;
    }

    public void setPoi(Poi poi) {
        this.poi = poi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visitaPoiPK != null ? visitaPoiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisitaPoi)) {
            return false;
        }
        VisitaPoi other = (VisitaPoi) object;
        if ((this.visitaPoiPK == null && other.visitaPoiPK != null) || (this.visitaPoiPK != null && !this.visitaPoiPK.equals(other.visitaPoiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.VisitaPoi[ visitaPoiPK=" + visitaPoiPK + " ]";
    }
    
}
