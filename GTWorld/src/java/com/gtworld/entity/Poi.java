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
@Table(name = "poi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Poi.findAll", query = "SELECT p FROM Poi p ORDER BY p.idPoi DESC"),
    @NamedQuery(name = "Poi.findByIdPoi", query = "SELECT p FROM Poi p WHERE p.idPoi = :idPoi"),
    @NamedQuery(name = "Poi.findByNombrePoi", query = "SELECT p FROM Poi p WHERE p.nombrePoi = :nombrePoi"),
    @NamedQuery(name = "Poi.findByDescripcionPoi", query = "SELECT p FROM Poi p WHERE p.descripcionPoi = :descripcionPoi"),
    @NamedQuery(name = "Poi.findByUrlWebPoi", query = "SELECT p FROM Poi p WHERE p.urlWebPoi = :urlWebPoi"),
    @NamedQuery(name = "Poi.findByFechaCreacionPoi", query = "SELECT p FROM Poi p WHERE p.fechaCreacionPoi = :fechaCreacionPoi"),
    @NamedQuery(name = "Poi.findByPrivacidadPoi", query = "SELECT p FROM Poi p WHERE p.privacidadPoi = :privacidadPoi")})
public class Poi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_POI", insertable = false, nullable = false, updatable = true)
    private Long idPoi;
    @Size(max = 100)
    @Column(name = "NOMBRE_POI")
    private String nombrePoi;
    @Size(max = 500)
    @Column(name = "DESCRIPCION_POI")
    private String descripcionPoi;
    @Size(max = 100)
    @Column(name = "URL_WEB_POI")
    private String urlWebPoi;
    @Column(name = "FECHA_CREACION_POI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacionPoi;
    @Column(name = "PRIVACIDAD_POI")
    private Boolean privacidadPoi;
    @JoinColumn(name = "ID_UBICACION", referencedColumnName = "ID_UBICACION")
    @ManyToOne(optional = false)
    private Ubicacion idUbicacion;
    @JoinColumn(name = "ID_TIPO_POI", referencedColumnName = "ID_TIPO_POI")
    @ManyToOne(optional = false)
    private TipoPoi idTipoPoi;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @OneToMany(mappedBy = "idPoi")
    private List<Imagen> imagenList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poi")
    private List<VisitaPoi> visitaPoiList;
    public Poi() {
    }

    public Poi(Long idPoi) {
        this.idPoi = idPoi;
    }

    public Long getIdPoi() {
        return idPoi;
    }

    public void setIdPoi(Long idPoi) {
        this.idPoi = idPoi;
    }

    public String getNombrePoi() {
        return nombrePoi;
    }

    public void setNombrePoi(String nombrePoi) {
        this.nombrePoi = nombrePoi;
    }

    public String getDescripcionPoi() {
        return descripcionPoi;
    }

    public void setDescripcionPoi(String descripcionPoi) {
        this.descripcionPoi = descripcionPoi;
    }

    public String getUrlWebPoi() {
        return urlWebPoi;
    }

    public void setUrlWebPoi(String urlWebPoi) {
        this.urlWebPoi = urlWebPoi;
    }

    public Date getFechaCreacionPoi() {
        return fechaCreacionPoi;
    }

    public void setFechaCreacionPoi(Date fechaCreacionPoi) {
        this.fechaCreacionPoi = fechaCreacionPoi;
    }

    public Boolean getPrivacidadPoi() {
        return privacidadPoi;
    }

    public void setPrivacidadPoi(Boolean privacidadPoi) {
        this.privacidadPoi = privacidadPoi;
    }

    public Ubicacion getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Ubicacion idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public TipoPoi getIdTipoPoi() {
        return idTipoPoi;
    }

    public void setIdTipoPoi(TipoPoi idTipoPoi) {
        this.idTipoPoi = idTipoPoi;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<Imagen> getImagenList() {
        return imagenList;
    }

    public void setImagenList(List<Imagen> imagenList) {
        this.imagenList = imagenList;
    }

    @XmlTransient
    public List<VisitaPoi> getVisitaPoiList() {
        return visitaPoiList;
    }

    public void setVisitaPoiList(List<VisitaPoi> visitaPoiList) {
        this.visitaPoiList = visitaPoiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPoi != null ? idPoi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Poi)) {
            return false;
        }
        Poi other = (Poi) object;
        if ((this.idPoi == null && other.idPoi != null) || (this.idPoi != null && !this.idPoi.equals(other.idPoi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.Poi[ idPoi=" + idPoi + " ]";
    }
    
}
