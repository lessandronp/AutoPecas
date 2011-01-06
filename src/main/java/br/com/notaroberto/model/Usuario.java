
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.notaroberto.model;

//~--- JDK imports ------------------------------------------------------------
import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Lessandro
 */
@Entity
@Table(name = "tb_usuario", catalog = "autopecas", schema = "", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"NM_USUARIO"})})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO", nullable = false)
    private Integer idUsuario;
    @Column(name = "DS_BAIRRO", nullable = false, length = 50)
    private String dsBairro;
    @Column(name = "DS_CIDADE", nullable = false, length = 50)
    private String dsCidade;
    @Column(name = "DS_ESTADO", nullable = false, length = 2)
    private String dsEstado;
    @Column(name = "DS_LOGIN", nullable = false, length = 20)
    private String dsLogin;
    @Column(name = "DS_RUA", nullable = false, length = 100)
    private String dsRua;
    @Column(name = "DS_SENHA", nullable = false, length = 32)
    private String dsSenha;
    @Column(name = "NM_USUARIO", nullable = false, length = 100)
    private String nmUsuario;
    @Column(name = "NU_CEP")
    private Integer nuCep;
    @Column(name = "NU_CPF", nullable = false, length = 11)
    private String nuCpf;
    @Column(name = "NU_TEL_FIXO", columnDefinition="bigint", nullable = false)
    private BigInteger nuTelFixo;
    @Column(name = "NU_TEL_CONTATO", columnDefinition="bigint", nullable = false)
    private BigInteger nuTelContato;
    @Column(name = "NU_LOCAL", nullable = false)
    private int nuLocal;
    @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID_PERFIL", nullable = false)
    @ManyToOne(optional = false)
    private Perfil perfil;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNmUsuario() {
        return nmUsuario;
    }

    public void setNmUsuario(String nmUsuario) {
        this.nmUsuario = nmUsuario;
    }

    public String getDsRua() {
        return dsRua;
    }

    public void setDsRua(String dsRua) {
        this.dsRua = dsRua;
    }

    public int getNuLocal() {
        return nuLocal;
    }

    public void setNuLocal(int nuLocal) {
        this.nuLocal = nuLocal;
    }

    public String getDsBairro() {
        return dsBairro;
    }

    public void setDsBairro(String dsBairro) {
        this.dsBairro = dsBairro;
    }

    public Integer getNuCep() {
        return nuCep;
    }

    public void setNuCep(Integer nuCep) {
        this.nuCep = nuCep;
    }

    public String getDsCidade() {
        return dsCidade;
    }

    public void setDsCidade(String dsCidade) {
        this.dsCidade = dsCidade;
    }

    public String getDsEstado() {
        return dsEstado;
    }

    public void setDsEstado(String dsEstado) {
        this.dsEstado = dsEstado;
    }

    public String getDsLogin() {
        return dsLogin;
    }

    public void setDsLogin(String dsLogin) {
        this.dsLogin = dsLogin;
    }

    public String getDsSenha() {
        return dsSenha;
    }

    public void setDsSenha(String dsSenha) {
        this.dsSenha = dsSenha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getNuCpf() {
        return nuCpf;
    }

    public void setNuCpf(String nuCpf) {
        this.nuCpf = nuCpf;
    }

    public BigInteger getNuTelFixo() {
        return nuTelFixo;
    }

    public void setNuTelFixo(BigInteger nuTelFixo) {
        this.nuTelFixo = nuTelFixo;
    }

    public BigInteger getNuTelContato() {
        return nuTelContato;
    }

    public void setNuTelContato(BigInteger nuTelContato) {
        this.nuTelContato = nuTelContato;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        hash += ((idUsuario != null)
                ? idUsuario.hashCode()
                : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }

        Usuario other = (Usuario) object;

        if (((this.idUsuario == null) && (other.idUsuario != null))
                || ((this.idUsuario != null) && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "br.com.notaroberto.model.Usuario[idUsuario=" + idUsuario + "]";
    }
}
