package br.com.notaroberto.dao;

import br.com.notaroberto.model.Usuario;

public interface IUsuarioDAO extends IGenericDAO<Usuario, Long>{
	
	public Usuario buscaLoginSenha(String login, String senha);

}
