package br.com.notaroberto.dao.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.notaroberto.dao.IUsuarioDAO;
import br.com.notaroberto.model.Usuario;
import br.com.notaroberto.util.CriptografaSenha;

@Repository
public class UsuarioDAO extends GenericDAO<Usuario, Long> implements IUsuarioDAO {

    public UsuarioDAO() {
    }

    @Autowired
    public UsuarioDAO(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public Usuario buscaLoginSenha(String login, String senha) {
        return buscaUmPeloCriterio(Expression.eq("dsLogin", login), Expression.eq("dsSenha", CriptografaSenha.criptografar(senha)));
    }
}
