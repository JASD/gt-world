/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "ubicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ubicacion.findAll", query = "SELECT u FROM Ubicacion u"),
    @NamedQuery(name = "Ubicacion.findByIdUbicacion", query = "SELECT u FROM Ubicacion u WHERE u.idUbicacion = :idUbicacion"),
    @NamedQuery(name = "Ubicacion.findByLatitudUbicacion", query = "SELECT u FROM Ubicacion u WHERE u.latitudUbicacion = :latitudUbicacion"),
    @NamedQuery(name = "Ubicacion.findByLongitudUbicacion", query = "SELECT u FROM Ubicacion u WHERE u.longitudUbicacion = :longitudUbicacion"),
    @NamedQuery(name = "Ubicacion.findByAltitudUbicacion", query = "SELECT u FROM Ubicacion u WHERE u.altitudUbicacion = :altitudUbicacion")})
public class Ubicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UBICACION")
    private Long idUbicacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "LATITUD_UBICACION")
    private Float latitudUbicacion;
    @Column(name = "LONGITUD_UBICACION")
    private Float longitudUbicacion;
    @Column(name = "ALTITUD_UBICACION")
    private Float altitudUbicacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUbicacion")
    private List<Poi> poiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ubicacion")
    private List<VisitaUbicacion> visitaUbicacionList;

    public Ubicacion() {
    }

    public Ubicacion(Long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Long getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public Float getLatitudUbicacion() {
        return latitudUbicacion;
    }

    public void setLatitudUbicacion(Float latitudUbicacion) {
        this.latitudUbicacion = latitudUbicacion;
    }

    public Float getLongitudUbicacion() {
        return longitudUbicacion;
    }

    public void setLongitudUbicacion(Float longitudUbicacion) {
        this.longitudUbicacion = longitudUbicacion;
    }

    public Float getAltitudUbicacion() {
        return altitudUbicacion;
    }

    public void setAltitudUbicacion(Float altitudUbicacion) {
        this.altitudUbicacion = altitudUbicacion;
    }

    @XmlTransient
    public List<Poi> getPoiList() {
        return poiList;
    }

    public void setPoiList(List<Poi> poiList) {
        this.poiList = poiList;
    }

    @XmlTransient
    public List<VisitaUbicacion> getVisitaUbicacionList() {
        return visitaUbicacionList;
    }

    public void setVisitaUbicacionList(List<VisitaUbicacion> visitaUbicacionList) {
        this.visitaUbicacionList = visitaUbicacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUbicacion != null ? idUbicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ubicacion)) {
            return false;
        }
        Ubicacion other = (Ubicacion) object;
        if ((this.idUbicacion == null && other.idUbicacion != null) || (this.idUbicacion != null && !this.idUbicacion.equals(other.idUbicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.Ubicacion[ idUbicacion=" + idUbicacion + " ]";
    }
    
}
