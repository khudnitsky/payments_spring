package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.constants.ServiceConstants;
import by.pvt.khudnitsky.payments.dao.IAccessLevelDao;
import by.pvt.khudnitsky.payments.dao.IAccountDao;
import by.pvt.khudnitsky.payments.dao.ICurrencyDao;
import by.pvt.khudnitsky.payments.dao.IUserDao;
import by.pvt.khudnitsky.payments.pojos.AccessLevel;
import by.pvt.khudnitsky.payments.pojos.Currency;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.pojos.Account;
import by.pvt.khudnitsky.payments.pojos.User;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Implementation of interface IUserService
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DaoException.class)
public class UserServiceImpl extends AbstractService<User> implements IUserService{
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private IAccountDao accountDao;
    @Autowired
    private IAccessLevelDao accessLevelDao;
    @Autowired
    private ICurrencyDao currencyDao;
    private IUserDao userDao;

    @Autowired
    public UserServiceImpl(IUserDao userDao){
        super(userDao);
        this.userDao = userDao;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User getUserByLogin(String login) throws ServiceException {
        User user;
        try {
            user = userDao.getByLogin(login);
        }
        catch (DaoException e) {
            logger.error(ServiceConstants.TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(ServiceConstants.TRANSACTION_FAILED + e);
        }
        return user;
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean checkIsNewUser(String login, Long accountNumber) throws ServiceException {
        boolean isNew = false;
        try {
            User user = userDao.getByLogin(login);
            Account account = accountDao.getByNumber(accountNumber);
            if((user == null) & (account == null)){
                isNew = true;
            }
            logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
            logger.info("User with login " + login + " is new");
        }
        catch (DaoException e) {
            logger.error(ServiceConstants.TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(ServiceConstants.TRANSACTION_FAILED + e);
        }
        return isNew;
    }

    @Override
    public void bookUser(User user, Account account) throws ServiceException {
        try {
            AccessLevel accessLevel = accessLevelDao.getByAccessLevelType(AccessLevelType.CLIENT);
            Currency currency = currencyDao.getByCurrencyType(account.getCurrency().getCurrencyType());
            currency.addAccount(account);
            account.setCurrency(currency);
            user.addAccount(account);
            account.setUser(user);
            user.addAccessLevel(accessLevel);
            accessLevel.addUser(user);
            accessLevelDao.save(accessLevel);
            currencyDao.save(currency);
            userDao.save(user);
            accountDao.save(account);
            logger.info(ServiceConstants.TRANSACTION_SUCCEEDED);
            logger.info(user);
            logger.info(account);
        }
        catch (DaoException e) {
            logger.error(ServiceConstants.TRANSACTION_FAILED, e);
            throw new ServiceException(ServiceConstants.TRANSACTION_FAILED + e);
        }
    }
}
