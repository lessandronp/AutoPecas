package br.com.notaroberto.controller;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.notaroberto.dao.IUsuarioDAO;
import br.com.notaroberto.model.Usuario;
import br.com.notaroberto.util.Constantes;
import br.com.notaroberto.util.JsfUtil;
import java.io.Serializable;

/**
 *
 * @author Lessandro
 */
@Named("autenticacaoController")
@Controller
@Scope("view")
public class AutenticacaoController implements Serializable {

    private Usuario usuario, usuarioAutenticado;
    private IUsuarioDAO usuarioDAO;
    private boolean exibeDialog;
    private HttpSession session;

    @PostConstruct
    public void init() {
        /* Chamado só quando o managed bean é colocado no escopo view,
        e não a cada requisição como acontecia com o escopo request */
        usuario = new Usuario();
    }

    @PreDestroy
    public void destroy() {
        /* chamado quando outra view for chamada através do UIViewRoot.setViewId(String viewId) */
    }

    @Autowired
    public AutenticacaoController(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioAutenticado() {
        recuperaSessao();
        if (session.getAttribute("usuarioAutenticado") != null) {
            usuarioAutenticado = (Usuario) session.getAttribute("usuarioAutenticado");
        }
        return usuarioAutenticado;
    }

    public void setUsuarioAutenticado(Usuario usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
    }

    public boolean isExibeDialog() {
        recuperaSessao();
        if (session.getAttribute("exibeDialog") != null) {
            exibeDialog = (Boolean) session.getAttribute("exibeDialog");
        }
        return exibeDialog;
    }

    public void setExibeDialog(boolean exibeDialog) {
        this.exibeDialog = exibeDialog;
    }

    public String autenticaUsuario() {
        try {
            usuarioAutenticado = usuarioDAO.buscaLoginSenha(usuario.getDsLogin(), usuario.getDsSenha());
            if (usuarioAutenticado == null) {
                JsfUtil.addErrorMessage("iphMensagem", ResourceBundle.getBundle("/message").getString("usuario.naoEncontrado"));
            } else {
                FacesContext ctx = FacesContext.getCurrentInstance();
                session = (HttpSession) ctx.getExternalContext().getSession(false);
                session.setAttribute("usuarioAutenticado", usuarioAutenticado);
                return Constantes.URL_MENU_HOME;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("iphMensagem", ResourceBundle.getBundle("/message").getString("mensagem.conexao.erro"));
        }
        return "";
    }

    public void encerraSessao() {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            session = (HttpSession) ctx.getExternalContext().getSession(false);
            session.setAttribute("usuarioAutenticado", null);
            ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/app" + Constantes.PAGINA_INDEX);
            session.invalidate();
        } catch (Exception e) {
        }
    }

    public void recuperaSessao() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        session = request.getSession();
    }

    public void fechaModal() {
        recuperaSessao();
        session.setAttribute("exibeDialog", false);
    }
}
