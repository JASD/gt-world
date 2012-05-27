/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.entity;

import java.io.Serializable;
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
@Table(name = "tipo_poi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPoi.findAll", query = "SELECT t FROM TipoPoi t"),
    @NamedQuery(name = "TipoPoi.findByIdTipoPoi", query = "SELECT t FROM TipoPoi t WHERE t.idTipoPoi = :idTipoPoi"),
    @NamedQuery(name = "TipoPoi.findByNombreTipoPoi", query = "SELECT t FROM TipoPoi t WHERE t.nombreTipoPoi = :nombreTipoPoi"),
    @NamedQuery(name = "TipoPoi.findByUrlIconoPoi", query = "SELECT t FROM TipoPoi t WHERE t.urlIconoPoi = :urlIconoPoi")})
public class TipoPoi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TIPO_POI")
    private Long idTipoPoi;
    @Size(max = 50)
    @Column(name = "NOMBRE_TIPO_POI")
    private String nombreTipoPoi;
    @Size(max = 100)
    @Column(name = "URL_ICONO_POI")
    private String urlIconoPoi;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoPoi")
    private List<Poi> poiList;

    public TipoPoi() {
    }

    public TipoPoi(Long idTipoPoi) {
        this.idTipoPoi = idTipoPoi;
    }

    public Long getIdTipoPoi() {
        return idTipoPoi;
    }

    public void setIdTipoPoi(Long idTipoPoi) {
        this.idTipoPoi = idTipoPoi;
    }

    public String getNombreTipoPoi() {
        return nombreTipoPoi;
    }

    public void setNombreTipoPoi(String nombreTipoPoi) {
        this.nombreTipoPoi = nombreTipoPoi;
    }

    public String getUrlIconoPoi() {
        return urlIconoPoi;
    }

    public void setUrlIconoPoi(String urlIconoPoi) {
        this.urlIconoPoi = urlIconoPoi;
    }

    @XmlTransient
    public List<Poi> getPoiList() {
        return poiList;
    }

    public void setPoiList(List<Poi> poiList) {
        this.poiList = poiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPoi != null ? idTipoPoi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPoi)) {
            return false;
        }
        TipoPoi other = (TipoPoi) object;
        if ((this.idTipoPoi == null && other.idTipoPoi != null) || (this.idTipoPoi != null && !this.idTipoPoi.equals(other.idTipoPoi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.TipoPoi[ idTipoPoi=" + idTipoPoi + " ]";
    }
    
}
