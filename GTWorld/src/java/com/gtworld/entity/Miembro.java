/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Antonio
 */
@Entity
@Table(name = "miembro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Miembro.findAll", query = "SELECT m FROM Miembro m"),
    @NamedQuery(name = "Miembro.findByIdUsuario", query = "SELECT m FROM Miembro m WHERE m.miembroPK.idUsuario = :idUsuario"),
    @NamedQuery(name = "Miembro.findByIdRed", query = "SELECT m FROM Miembro m WHERE m.miembroPK.idRed = :idRed"),
    @NamedQuery(name = "Miembro.findByFechaMiembro", query = "SELECT m FROM Miembro m WHERE m.fechaMiembro = :fechaMiembro")})
public class Miembro implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MiembroPK miembroPK;
    @Column(name = "FECHA_MIEMBRO")
    @Temporal(TemporalType.DATE)
    private Date fechaMiembro;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "ID_RED", referencedColumnName = "ID_RED", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Red red;

    public Miembro() {
    }

    public Miembro(MiembroPK miembroPK) {
        this.miembroPK = miembroPK;
    }

    public Miembro(String idUsuario, long idRed) {
        this.miembroPK = new MiembroPK(idUsuario, idRed);
    }

    public MiembroPK getMiembroPK() {
        return miembroPK;
    }

    public void setMiembroPK(MiembroPK miembroPK) {
        this.miembroPK = miembroPK;
    }

    public Date getFechaMiembro() {
        return fechaMiembro;
    }

    public void setFechaMiembro(Date fechaMiembro) {
        this.fechaMiembro = fechaMiembro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Red getRed() {
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (miembroPK != null ? miembroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Miembro)) {
            return false;
        }
        Miembro other = (Miembro) object;
        if ((this.miembroPK == null && other.miembroPK != null) || (this.miembroPK != null && !this.miembroPK.equals(other.miembroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtworld.entity.Miembro[ miembroPK=" + miembroPK + " ]";
    }
    
}
