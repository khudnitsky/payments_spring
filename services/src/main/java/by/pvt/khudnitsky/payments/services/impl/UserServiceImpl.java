package by.pvt.khudnitsky.payments.services.impl;

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

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class UserServiceImpl extends AbstractService<User> implements IUserService{
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);
    private static UserServiceImpl instance;
    private UserDaoImpl userDao = UserDaoImpl.getInstance();
    private AccountDaoImpl accountDao = AccountDaoImpl.getInstance();

    private UserServiceImpl(){}

    public static synchronized UserServiceImpl getInstance(){
        if(instance == null){
            instance = new UserServiceImpl();
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
    public Serializable save(User entity) throws ServiceException {
        Serializable id;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            id = userDao.save(entity);
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
    public List<User> getAll() throws ServiceException {
        List<User> users;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            users = userDao.getAll();
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info(users);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
        return users;
    }

    /**
     * Calls Dao getById() method
     *
     * @param id - id of entity
     * @return object of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public User getById(Long id) throws ServiceException {
        User user;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            user = userDao.getById(id);
            transaction.commit();
            logger.info(user);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
        return user;
    }

    /**
     * Calls Dao update() method
     *
     * @param entity - object of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public void update(User entity) throws ServiceException {
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            userDao.update(entity);
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info(entity);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
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
            userDao.delete(id);
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info("Deleted user #" + id);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
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

    public boolean checkIsNewUser(User user) throws ServiceException {
        boolean isNew = false;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if((userDao.getByLogin(user.getLogin()) == null) /*& (user.getAccounts() == null)*/){
                isNew = true;
            }
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info("User " + user + " is new");
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

            AccessLevelDaoImpl.getInstance().save(accessLevel);        // TODO
            CurrencyDaoImpl.getInstance().save(account.getCurrency()); // TODO
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
