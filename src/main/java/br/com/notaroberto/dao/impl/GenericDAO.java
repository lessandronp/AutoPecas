package br.com.notaroberto.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.notaroberto.dao.IGenericDAO;

public abstract class GenericDAO<T, ID extends Serializable> extends HibernateDaoSupport implements IGenericDAO<T, ID> {

    private static Log LOG = LogFactory.getLog(GenericDAO.class);

    @SuppressWarnings("unchecked")
    public GenericDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    private Class<T> persistentClass;

    public Class<T> getPersistentClass() {
        return this.persistentClass;
    }

    @Override
    public void exclui(T entity) {
        try {
            getHibernateTemplate().delete(entity);
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
    }

    @Override
    public T buscaPeloID(ID id) {
        try {
            return (T) getHibernateTemplate().get(getPersistentClass(), id);
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
        return null;
    }

    @Override
    public List<T> buscaTodos() {
        try {
            return getHibernateTemplate().loadAll(persistentClass);
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
        return null;
    }

    @Override
    public T salva(T entity) {
        try {
            getHibernateTemplate().save(entity);
            return entity;
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
        return entity;
    }

    @Override
    public T atualiza(T entity) {
        try {
            getHibernateTemplate().update(entity);
            return entity;
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
        return entity;
    }

    @Override
    public List<T> buscaListaPeloCriterio(Criterion... criterion) {
        try {
            Session session = getSession();
            Criteria crit = session.createCriteria(getPersistentClass());
            for (Criterion c : criterion) {
                crit.add(c);
            }
            return crit.list();
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
        return null;
    }

    @Override
    public T buscaUmPeloCriterio(Criterion... criterion) {
        try {
            Session session = getSession();
            Criteria crit = session.createCriteria(getPersistentClass());
            for (Criterion c : criterion) {
                crit.add(c);
            }
            return (T) crit.uniqueResult();
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
        return null;
    }
}
