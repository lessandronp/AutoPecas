
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notaroberto.model;

//~--- JDK imports ------------------------------------------------------------
import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Lessandro
 */
@Entity
@Table(name = "tb_perfil", catalog = "autopecas", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"NM_PERFIL"})})
public class Perfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERFIL", nullable = false)
    private Integer idPerfil;
    @Column(name = "DS_PERFIL", length = 60)
    private String dsPerfil;
    @Column(name = "NM_PERFIL", nullable = false, length = 20)
    private String nmPerfil;

    public Perfil() {
    }

    public Perfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Perfil(Integer idPerfil, String nmPerfil) {
        this.idPerfil = idPerfil;
        this.nmPerfil = nmPerfil;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNmPerfil() {
        return nmPerfil;
    }

    public void setNmPerfil(String nmPerfil) {
        this.nmPerfil = nmPerfil;
    }

    public String getDsPerfil() {
        return dsPerfil;
    }

    public void setDsPerfil(String dsPerfil) {
        this.dsPerfil = dsPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        hash += ((idPerfil != null)
                ? idPerfil.hashCode()
                : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Perfil)) {
            return false;
        }

        Perfil other = (Perfil) object;

        if (((this.idPerfil == null) && (other.idPerfil != null))
                || ((this.idPerfil != null) && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "br.com.notaroberto.model.Perfil[idPerfil=" + idPerfil + "]";
    }
}
//~ Formatted by Jindent --- http://www.jindent.com

