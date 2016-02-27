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

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */

@Service
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
    private UserServiceImpl(IUserDao userDao){
        super(userDao);
        this.userDao = userDao;
    }

    public boolean checkUserAuthorization(String login, String password) throws ServiceException {
        boolean isAuthorized = false;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            isAuthorized = userDao.isAuthorized(login, password);
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info("User " + login + " is authorized");
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
        return isAuthorized;
    }

    public User getUserByLogin(String login) throws ServiceException {
        User user;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            user = userDao.getByLogin(login);
            transaction.commit();
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
        return user;
    }

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

    public boolean checkIsNewUser(String login) throws ServiceException {
        boolean isNew = false;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if((userDao.getByLogin(login) == null) /*& (user.getAccounts() == null)*/){
                isNew = true;
            }
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info("User with login " + login + " is new");
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
        return isNew;
    }

    public void bookUser(User user, Account account) throws ServiceException {
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

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
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info(user);
            logger.info(account);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
    }
}
