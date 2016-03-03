package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.constants.ServiceConstants;
import by.pvt.khudnitsky.payments.dao.IAccountDao;
import by.pvt.khudnitsky.payments.dao.IOperationDao;
import by.pvt.khudnitsky.payments.pojos.Account;
import by.pvt.khudnitsky.payments.pojos.Operation;
import by.pvt.khudnitsky.payments.pojos.User;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.IAccountService;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Implementation of interface IAccountService
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DaoException.class)
public class AccountServiceImpl extends AbstractService<Account> implements IAccountService{
    private static Logger logger = Logger.getLogger(AccountServiceImpl.class);

    @Autowired
    private IOperationDao operationDao;
    private IAccountDao accountDao;

    @Autowired
    public AccountServiceImpl(IAccountDao accountDao){
        super(accountDao);
        this.accountDao = accountDao;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Account> getBlockedAccounts() throws ServiceException {
        List<Account> accounts;
        try {
            accounts = accountDao.getBlockedAccounts();
            logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
            logger.info(accounts);
        }
        catch (DaoException e) {
            logger.error(ServiceConstants.TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(ServiceConstants.TRANSACTION_FAILED + e);
        }
        return accounts;
    }

    @Override
    public void updateAccountStatus(Long accountNumber, AccountStatusType status) throws ServiceException {
        try {
            Account account = accountDao.getByNumber(accountNumber);
            account.setAccountStatus(status);
            accountDao.update(account);
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
    public boolean checkAccountStatus(Long id) throws ServiceException{
        boolean isBlocked;
        try {
            isBlocked = accountDao.isAccountStatusBlocked(id);
            logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
        }
        catch (DaoException e) {
            logger.error(ServiceConstants.TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(ServiceConstants.TRANSACTION_FAILED + e);
        }
        return isBlocked;
    }

    @Override
    public void addFunds(User user, String description, Double amount) throws ServiceException{
        try {
            Operation operation = EntityBuilder.buildOperation(user, description, amount);
            operationDao.save(operation);
            // TODO сделать множественность счетов
            Set<Account> accounts = user.getAccounts();
            Long accountId = 0L;
            Iterator<Account> iterator = accounts.iterator();
            while (iterator.hasNext()){
                accountId = iterator.next().getId();
            }
            Account account = accountDao.getById(accountId);
            account.setDeposit(account.getDeposit() + amount);
            accountDao.update(account);
            logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
        }
        catch (DaoException e) {
            logger.error(ServiceConstants.TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(ServiceConstants.TRANSACTION_FAILED + e);
        }
    }

    @Override
    public void blockAccount(User user, String description) throws ServiceException {
        try {
            Operation operation = EntityBuilder.buildOperation(user, description, 0D);
            operationDao.save(operation);
            // TODO сделать множественность счетов
            Set<Account> accounts = user.getAccounts();
            Long accountId = 0L;
            Iterator<Account> iterator = accounts.iterator();
            while (iterator.hasNext()){
                accountId = iterator.next().getId();
            }
            Account account = accountDao.getById(accountId);
            account.setAccountStatus(AccountStatusType.BLOCKED);
            accountDao.update(account);
            logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
        }
        catch (DaoException e) {
            logger.error(ServiceConstants.TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(ServiceConstants.TRANSACTION_FAILED + e);
        }
    }

    @Override
    public void payment(User user, String description, Double amount) throws ServiceException {
        try {
            Operation operation = EntityBuilder.buildOperation(user, description, amount);
            operationDao.save(operation);
            // TODO сделать множественность счетов
            Set<Account> accounts = user.getAccounts();
            Long accountId = 0L;
            Iterator<Account> iterator = accounts.iterator();
            while (iterator.hasNext()){
                accountId = iterator.next().getId();
            }
            Account account = accountDao.getById(accountId);
            account.setDeposit(account.getDeposit() - amount);
            accountDao.update(account);
            logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
        }
        catch (DaoException e) {
            logger.error(ServiceConstants.TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(ServiceConstants.TRANSACTION_FAILED + e);
        }
    }
}
