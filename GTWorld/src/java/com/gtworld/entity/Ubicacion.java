/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlTransient;

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
    @NamedQuery(name = "Ubicacion.findByAltitudUbicacion", query = "SELECT u FROM Ubicacion u WHERE u.altitudUbicacion = :altitudUbicacion"),
    @NamedQuery(name = "Ubicacion.findByDireccionUbicacion", query = "SELECT u FROM Ubicacion u WHERE u.direccionUbicacion = :direccionUbicacion")})
public class Ubicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_UBICACION", insertable = false, nullable = false, updatable = true)
    private Long idUbicacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "LATITUD_UBICACION")
    private Double latitudUbicacion;
    @Column(name = "LONGITUD_UBICACION")
    private Double longitudUbicacion;
    @Column(name = "ALTITUD_UBICACION")
    private Double altitudUbicacion;
    @Size(max = 100)
    @Column(name = "DIRECCION_UBICACION")
    private String direccionUbicacion;
    
    /*Datos no relevantes para esta entidad
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUbicacion")
    private List<Poi> poiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ubicacion")
    private List<VisitaUbicacion> visitaUbicacionList;
    * 
    */

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

    public Double getLatitudUbicacion() {
        return latitudUbicacion;
    }

    public void setLatitudUbicacion(Double latitudUbicacion) {
        this.latitudUbicacion = latitudUbicacion;
    }

    public Double getLongitudUbicacion() {
        return longitudUbicacion;
    }

    public void setLongitudUbicacion(Double longitudUbicacion) {
        this.longitudUbicacion = longitudUbicacion;
    }

    public Double getAltitudUbicacion() {
        return altitudUbicacion;
    }

    public void setAltitudUbicacion(Double altitudUbicacion) {
        this.altitudUbicacion = altitudUbicacion;
    }
    
     public String getDireccionUbicacion() {
        return direccionUbicacion;
    }

    public void setDireccionUbicacion(String direccionUbicacion) {
        this.direccionUbicacion = direccionUbicacion;
    }

    /*
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
    * 
    */

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
