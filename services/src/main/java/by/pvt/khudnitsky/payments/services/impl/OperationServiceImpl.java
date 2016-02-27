package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.IOperationDao;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */

@Service
public class OperationServiceImpl extends AbstractService<Operation> implements IOperationService{
    private static Logger logger = Logger.getLogger(OperationServiceImpl.class);

    private IOperationDao operationDao;

    @Autowired
    private OperationServiceImpl(IOperationDao operationDao){
        super(operationDao);
        this.operationDao = operationDao;
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
