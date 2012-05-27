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
@Table(name = "imagen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Imagen.findAll", query = "SELECT i FROM Imagen i"),
    @NamedQuery(name = "Imagen.findByCorrelativoImagen", query = "SELECT i FROM Imagen i WHERE i.correlativoImagen = :correlativoImagen"),
    @NamedQuery(name = "Imagen.findByTituloImagen", query = "SELECT i FROM Imagen i WHERE i.tituloImagen = :tituloImagen"),
    @NamedQuery(name = "Imagen.findByDescripcionImagen", query = "SELECT i FROM Imagen i WHERE i.descripcionImagen = :descripcionImagen"),
    @NamedQuery(name = "Imagen.findByUrlImagen", query = "SELECT i FROM Imagen i WHERE i.urlImagen = :urlImagen")})
public class Imagen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "CORRELATIVO_IMAGEN")
    private Long correlativoImagen;
    @Size(max = 50)
    @Column(name = "TITULO_IMAGEN")
    private String tituloImagen;
    @Size(max = 250)
    @Column(name = "DESCRIPCION_IMAGEN")
    private String descripcionImagen;
    @Size(max = 100)
    @Column(name = "URL_IMAGEN")
    private String urlImagen;
    @JoinColumn(name = "ID_POI", referencedColumnName = "ID_POI")
    @ManyToOne
    private Poi idPoi;
    @OneToMany(mappedBy = "correlativoImagen")
    private List<Usuario> usuarioList;

    public Imagen() {
    }

    public Imagen(Long correlativoImagen) {
        this.correlativoImagen = correlativoImagen;
    }

    public Long getCorrelativoImagen() {
        return correlativoImagen;
    }

    public void setCorrelativoImagen(Long correlativoImagen) {
        this.correlativoImagen = correlativoImagen;
    }

    public String getTituloImagen() {
        return tituloImagen;
    }

    public void setTituloImagen(String tituloImagen) {
        this.tituloImagen = tituloImagen;
    }

    public String getDescripcionImagen() {
        return descripcionImagen;
    }

    public void setDescripcionImagen(String descripcionImagen) {
        this.descripcionImagen = descripcionImagen;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Poi getIdPoi() {
        return idPoi;
    }

    public void setIdPoi(Poi idPoi) {
        this.idPoi = idPoi;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (correlativoImagen != null ? correlativoImagen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imagen)) {
            return false;
        }
        Imagen other = (Imagen) object;
        if ((this.correlativoImagen == null && other.correlativoImagen != null) || (this.correlativoImagen != null && !this.correlativoImagen.equals(other.correlativoImagen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.Imagen[ correlativoImagen=" + correlativoImagen + " ]";
    }
    
}
