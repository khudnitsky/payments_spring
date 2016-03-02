package by.pvt.khudnitsky.payments.services;

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
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public abstract class AbstractService<T extends AbstractEntity> implements IService<T>{
    private static Logger logger = Logger.getLogger(AbstractService.class);
    protected final String TRANSACTION_SUCCEEDED = "Transaction succeeded";
    protected final String TRANSACTION_FAILED = "Error was thrown in service: ";
    private IDao<T> dao;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    //@Autowired
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
                    logger.info(TRANSACTION_SUCCEEDED);
                    logger.info("Deleted entity #" + id);
                }
                catch (DaoException e) {
                    logger.error(TRANSACTION_FAILED, e);
                    transactionStatus.setRollbackOnly();
                    //throw new ServiceException(TRANSACTION_FAILED + e);
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
                    logger.info(TRANSACTION_SUCCEEDED);
                    logger.info(list);
                }
                catch (DaoException e) {
                    logger.error(TRANSACTION_FAILED, e);
                    transactionStatus.setRollbackOnly();
                    //throw new ServiceException(TRANSACTION_FAILED + e);
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
                    logger.info(entity);
                }
                catch (DaoException e) {
                    logger.error(TRANSACTION_FAILED, e);
                    transactionStatus.setRollbackOnly();
                    //throw new ServiceException(TRANSACTION_FAILED + e);
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
                    logger.info(TRANSACTION_SUCCEEDED);
                    logger.info(entity);
                }
                catch (DaoException e) {
                    logger.error(TRANSACTION_FAILED, e);
                    transactionStatus.setRollbackOnly();
                    //throw new ServiceException(TRANSACTION_FAILED + e);
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
                    logger.info(TRANSACTION_SUCCEEDED);
                    logger.info(entity);
                } catch (DaoException e) {
                    logger.error(TRANSACTION_FAILED, e);
                    transactionStatus.setRollbackOnly();
                    //throw new ServiceException(TRANSACTION_FAILED + e);
                }
                return null;
            }
        });
    }
}
