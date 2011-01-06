package br.com.notaroberto.dao.impl;

//~--- non-JDK imports --------------------------------------------------------
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.notaroberto.dao.IPerfilDAO;
import br.com.notaroberto.model.Perfil;

/**
 *
 * @author Lessandro
 */
@Repository
public class PerfilDAO extends GenericDAO<Perfil, Long> implements IPerfilDAO {

    public PerfilDAO() {
    }

    @Autowired
    public PerfilDAO(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
