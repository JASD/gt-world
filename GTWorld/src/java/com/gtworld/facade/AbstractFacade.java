/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtworld.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Antonio
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;
    private int cont;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     * Método que devuelve un objeto de la Base de Datos
     *
     * @param namedQuery Nombre de la Consulta a Realizar
     * @param parameters Párametros de la Consulta
     * @return
     * @throws NoResultException
     */
    public T getSingleResult(String namedQuery, Object[] parameters)
            throws NoResultException {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        for (int i = 0; i <= (parameters.length - 2); i = i + 2) {
            q.setParameter((String) parameters[i], parameters[i + 1]);
        }
        return (T) q.getSingleResult();
    }

    /**
     * Método que devuelve una lista de objetos de la Base de Datos
     *
     * @param range rango de resultados
     * @param namedQuery Nombre de la Consulta a Realizar
     * @param parameters Párametros de la Consulta
     * @return
     */
    public List<T> findRange(int[] range, String namedQuery, Object[] parameters) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        for (int i = 0; i <= (parameters.length - 2); i = i + 2) {
            q.setParameter((String) parameters[i], parameters[i + 1]);
        }
        cont = q.getResultList().size();
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Método que devuelve una lista de objetos de la Base de Datos
     *
     * @param range rango de resultados
     * @param namedQuery Nombre de la Consulta a Realizar
     * @return
     */
    public List<T> findRange(int[] range, String namedQuery) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        cont = q.getResultList().size();
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Método que devuelve una lista de objetos de la Base de Datos
     *
     * @param namedQuery Nombre de la Consulta a Realizar
     * @param parameters Párametros de la Consulta
     * @return
     */
    public List<T> find(String namedQuery, Object[] parameters) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        for (int i = 0; i <= (parameters.length - 2); i = i + 2) {
            q.setParameter((String) parameters[i], parameters[i + 1]);
        }
        cont = q.getResultList().size();
        return q.getResultList();
    }

    /**
     * Método que devuelve una lista de objetos de la Base de Datos
     *
     * @param namedQuery Nombre de la Consulta a Realizar
     * @return
     */
    public List<T> find(String namedQuery) {
        Query q = getEntityManager().createNamedQuery(namedQuery);
        cont = q.getResultList().size();
        return q.getResultList();
    }

    public int getCont() {
        return cont;
    }
}
