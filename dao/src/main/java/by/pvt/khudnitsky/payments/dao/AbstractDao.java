/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.entities.AbstractEntity;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.util.List;

/**
 * Describes the abstract class <b>AbstractDao</b>
 * @param <T> - entity of <b>AbstractEntity</b>
 * @author khudnitsky
 * @version 1.0
 */

public abstract class AbstractDao<T extends AbstractEntity> implements IDao<T> {
    private static Logger logger = Logger.getLogger(AbstractDao.class);
    protected static HibernateUtil util = HibernateUtil.getInstance();
    private Class persistentClass;

    protected AbstractDao(Class persistentClass){
        this.persistentClass = persistentClass;
    }

    @Override
    public Serializable save(T entity) throws DaoException{
        Serializable id;
        try {
            Session session = util.getSession();
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
            Session session = util.getSession();
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
            Session session = util.getSession();
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
            Session session = util.getSession();
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
            Session session = util.getSession();
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
            Session session = util.getSession();
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
            Session session = util.getSession();
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
