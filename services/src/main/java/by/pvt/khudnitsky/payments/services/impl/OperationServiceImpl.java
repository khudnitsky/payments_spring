package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.impl.OperationDaoImpl;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.dto.OperationDTO;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.IOperationService;
import by.pvt.khudnitsky.payments.utils.TransactionUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class OperationServiceImpl extends AbstractService<Operation> implements IOperationService{
    private static Logger logger = Logger.getLogger(OperationServiceImpl.class);
    private static OperationServiceImpl instance;

    private OperationDaoImpl operationDao = OperationDaoImpl.getInstance();

    private OperationServiceImpl(){}

    public static synchronized OperationServiceImpl getInstance(){
        if(instance == null){
            instance = new OperationServiceImpl();
        }
        return instance;
    }

    /**
     * Calls Dao save() method
     *
     * @param entity - object of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public Serializable save(Operation entity) throws ServiceException {
        Serializable id;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            id = operationDao.save(entity);
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info(entity);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
        return id;
    }

    /**
     * Calls Dao getAll() method
     *
     * @return list of objects of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public List<Operation> getAll() throws ServiceException {
        List<Operation> operations;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            operations = operationDao.getAll();
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info(operations);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
        return operations;
    }

    /**
     * Calls Dao getById() method
     *
     * @param id - id of entity
     * @return object of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public Operation getById(Long id) throws ServiceException {
        Operation operation;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            operation = operationDao.getById(id);
            transaction.commit();
            logger.info(operation);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
        return operation;
    }

    /**
     * Calls Dao update() method
     *
     * @param entity - object of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public void update(Operation entity) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao delete() method
     *
     * @param id - id of entity
     * @throws SQLException
     */
    @Override
    public void delete(Long id) throws ServiceException {
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            operationDao.delete(id);
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info("Deleted operation #" + id);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
    }

    @Override
    public void deleteByAccountId(Long id) throws ServiceException {
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            operationDao.deleteByAccountId(id);
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
    }

    public int getNumberOfPages(int recordsPerPage) throws ServiceException{
        int numberOfPages;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Long numberOfRecords = operationDao.getAmount();
            numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
        return numberOfPages;
    }

    public List<OperationDTO> getAllToPage(int recordsPerPage, int pageNumber, String sorting) throws ServiceException {
        List<OperationDTO> results;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            results = operationDao.getOperations(recordsPerPage, pageNumber, sorting);
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
        return results;
    }
}
