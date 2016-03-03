package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.constants.ServiceConstants;
import by.pvt.khudnitsky.payments.dao.IDao;
import by.pvt.khudnitsky.payments.pojos.AbstractEntity;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes Abstract Service
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public abstract class AbstractService<T extends AbstractEntity> implements IService<T>{
    private static Logger logger = Logger.getLogger(AbstractService.class);
    private IDao<T> dao;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    protected AbstractService(IDao<T> dao){
        this.dao = dao;
    }

    @Override
    public void delete(Long id) throws ServiceException {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus transactionStatus){
                try {
                    dao.delete(id);
                    logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
                    logger.info(ServiceConstants.MESSAGE_DELETE + id);
                }
                catch (DaoException e) {
                    logger.error(ServiceConstants.TRANSACTION_FAILED, e);
                    transactionStatus.setRollbackOnly();
                }
                return null;
            }
        });
    }

    @Override
    public List<T> getAll() throws ServiceException {
        return transactionTemplate.execute(new TransactionCallback<List<T>>() {
            @Override
            public List<T> doInTransaction(TransactionStatus transactionStatus) {
                List<T> list = new ArrayList<T>();
                try {
                    list = dao.getAll();
                    logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
                    logger.info(list);
                }
                catch (DaoException e) {
                    logger.error(ServiceConstants.TRANSACTION_FAILED, e);
                    transactionStatus.setRollbackOnly();
                }
                return list;
            }
        });
    }

    @Override
    public T getById(Long id) throws ServiceException {
        return transactionTemplate.execute(new TransactionCallback<T>() {
            @Override
            public T doInTransaction(TransactionStatus transactionStatus) {
                T entity = null;
                try {
                    entity = (T) dao.getById(id);
                    logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
                    logger.info(entity);
                }
                catch (DaoException e) {
                    logger.error(ServiceConstants.TRANSACTION_FAILED, e);
                    transactionStatus.setRollbackOnly();
                }
                return entity;
            }
        });

    }

    @Override
    public Serializable save(T entity) throws ServiceException {
        return transactionTemplate.execute(new TransactionCallback<Serializable>() {
            @Override
            public Serializable doInTransaction(TransactionStatus transactionStatus) {
                Serializable id = null;
                try {
                    id = dao.save(entity);
                    logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
                    logger.info(entity);
                }
                catch (DaoException e) {
                    logger.error(ServiceConstants.TRANSACTION_FAILED, e);
                    transactionStatus.setRollbackOnly();
                }
                return id;
            }
        });
    }

    @Override
    public void update(T entity) throws ServiceException {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus transactionStatus) {
                try {
                    dao.update(entity);
                    logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
                    logger.info(entity);
                } catch (DaoException e) {
                    logger.error(ServiceConstants.TRANSACTION_FAILED, e);
                    transactionStatus.setRollbackOnly();
                }
                return null;
            }
        });
    }
}
