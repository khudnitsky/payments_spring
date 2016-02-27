/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.entities.AbstractEntity;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Describes the abstract class <b>AbstractDao</b>
 * @param <T> - entity of <b>AbstractEntity</b>
 * @author khudnitsky
 * @version 1.0
 */

//@Repository
public abstract class AbstractDao<T extends AbstractEntity> implements IDao<T> {
    private static Logger logger = Logger.getLogger(AbstractDao.class);
    private Class persistentClass;

    @Autowired
    private SessionFactory sessionFactory;

    protected AbstractDao(Class persistentClass, SessionFactory sessionFactory){
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
    }

    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Serializable save(T entity) throws DaoException{
        Serializable id;
        try {
            Session session = getCurrentSession();
            session.saveOrUpdate(entity);
            id = session.getIdentifier(entity);
        }
        catch(HibernateException e) {
            logger.error("Error was thrown in DAO: " + e);     // TODO вынести в message
            throw new DaoException();
        }
        return id;
    }

    @Override
    public List<T> getAll() throws DaoException {
        List<T> results;
        try {
            Session session = getCurrentSession();
            Criteria criteria = session.createCriteria(persistentClass);
            results = criteria.list();
        }
        catch(HibernateException e){
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException();
        }
        return results;
    }

    @Override
    public T getById(Long id) throws DaoException{
        T entity;
        try {
            Session session = getCurrentSession();
            entity = (T)session.get(persistentClass, id);
        }
        catch(HibernateException e){
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException();
        }
        return entity;
    }

    @Override
    public void update(T entity) throws DaoException{
        try {
            Session session = getCurrentSession();
            session.merge(entity);
        }
        catch(HibernateException e) {
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException();
        }
    }

    @Override
    public void delete(Long id) throws DaoException{
        try {
            Session session = getCurrentSession();
            T entity = (T) session.get(persistentClass, id);
            session.delete(entity);
        }
        catch(HibernateException e){
            //TODO исправить
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException(e.getMessage());
        }
        catch(IllegalArgumentException e){
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException();
        }
    }

    @Override
    public Long getAmount() throws DaoException{
        Long amount;
        try {
            Session session = getCurrentSession();
            Criteria criteria = session.createCriteria(persistentClass);
            Projection count = Projections.rowCount();
            criteria.setProjection(count);
            amount = (Long) criteria.uniqueResult();
        }
        catch(HibernateException e){
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException();
        }
        return amount;
    }

    public List<T> getAllToPage(int recordsPerPage, int pageNumber) throws DaoException {
        List<T> results;
        try {
            Session session = getCurrentSession();
            Criteria criteria = session.createCriteria(persistentClass);
            criteria.setFirstResult((pageNumber-1) * recordsPerPage);
            criteria.setMaxResults(recordsPerPage);
            results = criteria.list();
        }
        catch(HibernateException e){
            logger.error("Error was thrown in DAO: " + e);
            throw new DaoException();
        }
        return results;
    }
}
