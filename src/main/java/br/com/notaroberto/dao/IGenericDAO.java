package br.com.notaroberto.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

public interface IGenericDAO<T, ID extends Serializable> {

    public T buscaPeloID(ID id);

    public List<T> buscaTodos();

    public T salva(T entity);

    public T atualiza(T entity);

    public void exclui(T entity);

    public List<T> buscaListaPeloCriterio(Criterion... criterion);

    public T buscaUmPeloCriterio(Criterion... criterion);
}
