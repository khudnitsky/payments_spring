package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.IAccessLevelDao;
import by.pvt.khudnitsky.payments.dao.IAccountDao;
import by.pvt.khudnitsky.payments.dao.ICurrencyDao;
import by.pvt.khudnitsky.payments.dao.IUserDao;
import by.pvt.khudnitsky.payments.dao.impl.AccessLevelDaoImpl;
import by.pvt.khudnitsky.payments.dao.impl.CurrencyDaoImpl;
import by.pvt.khudnitsky.payments.entities.AccessLevel;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.dao.impl.AccountDaoImpl;
import by.pvt.khudnitsky.payments.dao.impl.UserDaoImpl;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.IUserService;
import by.pvt.khudnitsky.payments.utils.TransactionUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
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
    public boolean checkUserAuthorization(String login, String password) throws ServiceException {
        boolean isAuthorized;
        try {
            isAuthorized = userDao.isAuthorized(login, password);
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info("User " + login + " is authorized");
        }
        catch (DaoException e) {
            logger.error(TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
        return isAuthorized;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User getUserByLogin(String login) throws ServiceException {
        User user;
        try {
            user = userDao.getByLogin(login);
        }
        catch (DaoException e) {
            logger.error(TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public AccessLevelType checkAccessLevel(User user) throws ServiceException{
        AccessLevel accessLevel = new AccessLevel();
        accessLevel.setAccessLevelType(AccessLevelType.CLIENT);
        Set<AccessLevel> accessLevels = user.getAccessLevels();
        Iterator<AccessLevel> iterator = accessLevels.iterator();
        while (iterator.hasNext()) {
            if((iterator.next()).getAccessLevelType().equals(AccessLevelType.CLIENT)){
                accessLevel.setAccessLevelType(AccessLevelType.CLIENT);
            }
            else {
                accessLevel.setAccessLevelType(AccessLevelType.ADMINISTRATOR);
            }
        }
        return accessLevel.getAccessLevelType();
        //TODO сделать множественность ролей
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean checkIsNewUser(String login) throws ServiceException {
        boolean isNew = false;
        try {
            if((userDao.getByLogin(login) == null) /*& (user.getAccounts() == null)*/){
                isNew = true;
            }
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info("User with login " + login + " is new");
        }
        catch (DaoException e) {
            logger.error(TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
        return isNew;
    }

    @Override
    public void bookUser(User user, Account account) throws ServiceException {
        try {
            AccessLevel accessLevel = new AccessLevel();
            user.addAccount(account);
            account.setUser(user);
            accessLevel.setAccessLevelType(AccessLevelType.CLIENT);
            user.addAccessLevel(accessLevel);
            accessLevel.addUser(user);
            accessLevelDao.save(accessLevel);
            currencyDao.save(account.getCurrency());
            userDao.save(user);
            accountDao.save(account);
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info(user);
            logger.info(account);
        }
        catch (DaoException e) {
            logger.error(TRANSACTION_FAILED, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
    }
}
