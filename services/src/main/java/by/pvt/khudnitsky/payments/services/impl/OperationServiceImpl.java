package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.constants.ServiceConstants;
import by.pvt.khudnitsky.payments.dao.IOperationDao;
import by.pvt.khudnitsky.payments.pojos.Operation;
import by.pvt.khudnitsky.payments.dto.OperationDTO;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.IOperationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * Implementation of interface IOperationService
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DaoException.class)
public class OperationServiceImpl extends AbstractService<Operation> implements IOperationService{
    private static Logger logger = Logger.getLogger(OperationServiceImpl.class);
    private IOperationDao operationDao;

    @Autowired
    public OperationServiceImpl(IOperationDao operationDao){
        super(operationDao);
        this.operationDao = operationDao;
    }

    @Override
    public void deleteByAccountId(Long id) throws ServiceException {
        try {
            operationDao.deleteByAccountId(id);
            logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
        }
        catch (DaoException e) {
            logger.error(ServiceConstants.TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(ServiceConstants.TRANSACTION_FAILED + e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int getNumberOfPages(int recordsPerPage) throws ServiceException{
        int numberOfPages;
        try {
            Long numberOfRecords = operationDao.getAmount();
            numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
            logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
        }
        catch (DaoException e) {
            logger.error(ServiceConstants.TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(ServiceConstants.TRANSACTION_FAILED + e);
        }
        return numberOfPages;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<OperationDTO> getAllToPage(int recordsPerPage, int pageNumber, String sorting) throws ServiceException {
        List<OperationDTO> results;
        try {
            results = operationDao.getOperations(recordsPerPage, pageNumber, sorting);
            logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
        }
        catch (DaoException e) {
            logger.error(ServiceConstants.TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(ServiceConstants.TRANSACTION_FAILED + e);
        }
        return results;
    }
}
