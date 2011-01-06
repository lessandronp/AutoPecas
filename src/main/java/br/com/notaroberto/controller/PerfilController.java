package br.com.notaroberto.controller;

import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.notaroberto.dao.IPerfilDAO;
import br.com.notaroberto.model.Perfil;
import java.io.Serializable;

@Named("perfilController")
@Controller
@Scope("view")
public class PerfilController implements Serializable {

    private Perfil perfil;
    private IPerfilDAO perfilDAO;
    private List<Perfil> listaPerfis;
    private String mensagem;
    private boolean exibeBotaoIncluir, exibeBotaoAlterar,
            exibeBotaoExcluir, somenteLeitura,
            exibeListaPerfis = true, exibeDialog;

    @PostConstruct
    public void init() {
        /* Chamado só quando o managed bean é colocado no escopo view,
        e não a cada requisição como acontecia com o escopo request */
        setListaPerfis(perfilDAO.buscaTodos());
    }

    @PreDestroy
    public void destroy() {
        /* chamado quando outra view for chamada através do UIViewRoot.setViewId(String viewId) */
        setListaPerfis(null);
        setExibeBotaoIncluir(false);
        setExibeBotaoAlterar(false);
        setExibeBotaoExcluir(false);
        setExibeListaPerfis(true);
        setSomenteLeitura(false);
    }

    @Autowired
    public PerfilController(IPerfilDAO perfilDAO) {
        this.perfilDAO = perfilDAO;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public List<Perfil> getListaPerfis() {
        return listaPerfis;
    }

    public void setListaPerfis(List<Perfil> listaPerfis) {
        this.listaPerfis = listaPerfis;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isExibeBotaoIncluir() {
        return exibeBotaoIncluir;
    }

    public void setExibeBotaoIncluir(boolean exibeBotaoIncluir) {
        this.exibeBotaoIncluir = exibeBotaoIncluir;
    }

    public boolean isExibeBotaoAlterar() {
        return exibeBotaoAlterar;
    }

    public void setExibeBotaoAlterar(boolean exibeBotaoAlterar) {
        this.exibeBotaoAlterar = exibeBotaoAlterar;
    }

    public boolean isExibeBotaoExcluir() {
        return exibeBotaoExcluir;
    }

    public void setExibeBotaoExcluir(boolean exibeBotaoExcluir) {
        this.exibeBotaoExcluir = exibeBotaoExcluir;
    }

    public boolean isSomenteLeitura() {
        return somenteLeitura;
    }

    public void setSomenteLeitura(boolean somenteLeitura) {
        this.somenteLeitura = somenteLeitura;
    }

    public boolean isExibeListaPerfis() {
        return exibeListaPerfis;
    }

    public void setExibeListaPerfis(boolean exibeListaPerfis) {
        this.exibeListaPerfis = exibeListaPerfis;
    }

    public boolean isExibeDialog() {
        return exibeDialog;
    }

    public void setExibeDialog(boolean exibeDialog) {
        this.exibeDialog = exibeDialog;
    }

    public void preparaInclusao() {
        perfil = new Perfil();
        setExibeListaPerfis(false);
        setExibeBotaoIncluir(true);
    }

    public void preparaConsulta() {
        setExibeListaPerfis(false);
        setSomenteLeitura(true);
    }

    public void preparaAlteracao() {
        setExibeListaPerfis(false);
        setSomenteLeitura(false);
        setExibeBotaoAlterar(true);
    }

    public void preparaExclusao() {
        setExibeListaPerfis(false);
        setSomenteLeitura(true);
        setExibeBotaoExcluir(true);
    }

    public void carregaPerfisUsuarios() {
        setListaPerfis(perfilDAO.buscaTodos());
    }

    public void salvar(ActionEvent actionEvent) {
        setExibeDialog(false);
        try {
            perfilDAO.salva(perfil);
            setExibeDialog(true);
            setMensagem(ResourceBundle.getBundle("/message").getString("perfil.criadoSucesso"));
        } catch (Exception e) {
            setExibeDialog(true);
            setMensagem(e + " " + ResourceBundle.getBundle("/message").getString("perfil.erroIncluir"));
        }
    }

    public void alterar(ActionEvent actionEvent) {
        setExibeDialog(false);
        try {
            perfilDAO.atualiza(perfil);
            setExibeDialog(true);
            setMensagem(ResourceBundle.getBundle("/message").getString("perfil.alteradoSucesso"));
        } catch (Exception e) {
            setExibeDialog(true);
            setMensagem(e + " " + ResourceBundle.getBundle("/message").getString("perfil.erroAlterar"));
        }
    }

    public void excluir(ActionEvent actionEvent) {
        setExibeDialog(false);
        try {
            perfilDAO.exclui(perfil);
            setExibeDialog(true);
            setMensagem(ResourceBundle.getBundle("/message").getString("perfil.excluidoSucesso"));
        } catch (Exception e) {
            setExibeDialog(true);
            setMensagem(e + " " + ResourceBundle.getBundle("/message").getString("perfil.erroExcluir"));
        }
    }

    @FacesConverter(forClass = Perfil.class, value = "perfilConverter")
    public static class PerfilControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if ((value == null) || (value.length() == 0)) {
                return null;
            }

            PerfilController perfilController = (PerfilController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(),
                    null, "perfilController");

            return perfilController.perfilDAO.buscaPeloID(Long.parseLong(String.valueOf(getID(value))));
        }

        Integer getID(String value) {
            Integer id;
            id = Integer.valueOf(value);
            return id;
        }

        String getStringID(Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }

            if (object instanceof Perfil) {
                Perfil p = (Perfil) object;

                return getStringID(p.getIdPerfil());
            } else {
                throw new IllegalArgumentException("objeto " + object + " possui o tipo " + object.getClass().getName()
                        + "; tipo esperado: " + PerfilController.class.getName());
            }
        }
    }
}
