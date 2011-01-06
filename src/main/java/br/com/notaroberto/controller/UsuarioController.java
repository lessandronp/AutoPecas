package br.com.notaroberto.controller;

//~--- non-JDK imports --------------------------------------------------------
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

import br.com.notaroberto.dao.IUsuarioDAO;
import br.com.notaroberto.model.Perfil;
import br.com.notaroberto.model.Usuario;
import java.io.Serializable;

@Named("usuarioController")
@Controller
@Scope("view")
public class UsuarioController implements Serializable {

	private Usuario usuario;
	private IUsuarioDAO usuarioDAO;
	private PerfilController perfilController;
	private List<Usuario> listaUsuarios;
	private List<Perfil> listaPerfis;
	private String mensagem;
	private boolean exibeBotaoIncluir, exibeBotaoAlterar, exibeBotaoExcluir,
	somenteLeitura, exibeListaUsuarios = true, exibeDialog;

	@PostConstruct
	public void init() {
		/*
		 * Chamado só quando o managed bean é colocado no escopo view, e não a
		 * cada requisição como acontecia com o escopo request
		 */
		setListaUsuarios(usuarioDAO.buscaTodos());
		perfilController.carregaPerfisUsuarios();
		setListaPerfis(perfilController.getListaPerfis());
	}

	@PreDestroy
	public void destroy() {
		/*
		 * chamado quando outra view for chamada através do
		 * UIViewRoot.setViewId(String viewId)
		 */
		setListaUsuarios(null);
		setListaPerfis(null);
		setExibeBotaoIncluir(false);
		setExibeBotaoAlterar(false);
		setExibeBotaoExcluir(false);
		setExibeListaUsuarios(true);
		setSomenteLeitura(false);
	}

	@Autowired
	public UsuarioController(IUsuarioDAO usuarioDAO, PerfilController perfilController) {
		this.usuarioDAO = usuarioDAO;
		this.perfilController = perfilController;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public boolean isExibeListaUsuarios() {
		return exibeListaUsuarios;
	}

	public void setExibeListaUsuarios(boolean exibeListaUsuarios) {
		this.exibeListaUsuarios = exibeListaUsuarios;
	}

	public boolean isExibeDialog() {
		return exibeDialog;
	}

	public void setExibeDialog(boolean exibeDialog) {
		this.exibeDialog = exibeDialog;
	}

	public void preparaInclusao() {
		usuario = new Usuario();
		setExibeListaUsuarios(false);
		setExibeBotaoIncluir(true);
	}

	public void preparaConsulta() {
		setExibeListaUsuarios(false);
		setSomenteLeitura(true);
	}

	public void preparaAlteracao() {
		setExibeListaUsuarios(false);
		setSomenteLeitura(false);
		setExibeBotaoAlterar(true);
	}

	public void preparaExclusao() {
		setExibeListaUsuarios(false);
		setSomenteLeitura(true);
		setExibeBotaoExcluir(true);
	}

	public void salvar(ActionEvent actionEvent) {
		setExibeDialog(false);
		try {
			usuarioDAO.salva(usuario);
			setExibeDialog(true);
			setMensagem(ResourceBundle.getBundle("/message").getString("usuario.criadoSucesso"));
		} catch (Exception e) {
			setExibeDialog(true);
			setMensagem(e + " " + ResourceBundle.getBundle("/message").getString("usuario.erroIncluir"));
		}
	}

	public void alterar(ActionEvent actionEvent) {
		setExibeDialog(false);
		try {
			usuarioDAO.atualiza(usuario);
			setExibeDialog(true);
			setMensagem(ResourceBundle.getBundle("/message").getString("usuario.alteradoSucesso"));
		} catch (Exception e) {
			setExibeDialog(true);
			setMensagem(e + " " + ResourceBundle.getBundle("/message").getString("usuario.erroAlterar"));
		}
	}

	public void excluir(ActionEvent actionEvent) {
		setExibeDialog(false);
		try {
			usuarioDAO.exclui(usuario);
			setExibeDialog(true);
			setMensagem(ResourceBundle.getBundle("/message").getString("usuario.excluidoSucesso"));
		} catch (Exception e) {
			setExibeDialog(true);
			setMensagem(e + " " + ResourceBundle.getBundle("/message").getString("usuario.erroExcluir"));
		}
	}

	@FacesConverter(forClass = Usuario.class, value = "usuarioConverter")
	public static class UsuarioControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext,
				UIComponent component, String value) {
			if ((value == null) || (value.length() == 0)) {
				return null;
			}

			UsuarioController usuarioController = (UsuarioController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null,
					"usuarioController");

			return usuarioController.usuarioDAO.buscaPeloID(Long.parseLong(String.valueOf(getID(value))));
		}

		Integer getID(String value) {
			Integer id;
			id = Integer.valueOf(value);
			return id;
		}

		String getStringID(Integer value) {
			StringBuilder sb = new StringBuilder();
			sb.append(value);
			return sb.toString();
		}

		@Override
		public String getAsString(FacesContext facesContext,
				UIComponent component, Object object) {
			if (object == null) {
				return null;
			}

			if (object instanceof Usuario) {
				Usuario o = (Usuario) object;

				return getStringID(o.getIdUsuario());
			} else {
				throw new IllegalArgumentException("objeto " + object
						+ " possui o tipo " + object.getClass().getName()
						+ "; tipo esperado: "
						+ UsuarioController.class.getName());
			}
		}
	}
}
