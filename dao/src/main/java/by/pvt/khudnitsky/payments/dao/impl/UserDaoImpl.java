/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import java.util.List;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IUserDao;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class UserDaoImpl extends AbstractDao<User> implements IUserDao{
    private static Logger logger = Logger.getLogger(UserDaoImpl.class);
    private static UserDaoImpl instance;
    private final String GET_ALL_CLIENTS = " from User" /* user  join user.accessLevels level where level.accessLevelType = :accessLevelType"*/;
    private final String GET_BY_LOGIN = "from User where login = :login"; // TODO вынести в отдельный класс
    private final String CHECK_AUTHORIZATION = "from User where login = :login and password = :password";

    static String message;

    private UserDaoImpl(){
        super(User.class);
    }

    public static synchronized UserDaoImpl getInstance(){
        if(instance == null){
            instance = new UserDaoImpl();
        }
        return instance;
    }

    // TODO дописать тесты

    @Override
    public List<User> getAll() throws DaoException {
        List<User> results;
        try {
            Session session = util.getSession();
            Query query = session.createQuery(GET_ALL_CLIENTS);
            query.setCacheable(true);
            //query.setParameter("accessLevelType", AccessLevelType.CLIENT);
            results = query.list();
        }
        catch(HibernateException e){
            message = "Unable to return list of clients. Error was thrown in DAO: ";
            logger.error(message + e);
            throw new DaoException(message, e);
        }
        return results;
    }

    @Override
    public User getByLogin(String login) throws DaoException {
        User user;
        try {
            Session session = util.getSession();
            Query query = session.createQuery(GET_BY_LOGIN);
            query.setParameter("login", login);
            user = (User) query.uniqueResult();
        }
        catch(HibernateException e){
            message = "Unable to return user by login. Error was thrown in DAO: ";
            logger.error(message + e);
            throw new DaoException(message, e);
        }
        return user;
    }

    @Override
    public boolean isAuthorized(String login, String password) throws DaoException {
        boolean isLogIn = false;
        try {
            Session session = util.getSession();
            Query query = session.createQuery(CHECK_AUTHORIZATION);
            query.setParameter("login", login);
            query.setParameter("password", password);
            if(query.uniqueResult() != null){
                isLogIn = true;
            }
        }
        catch(HibernateException e){
            message = "Unable to check authorization. Error was thrown in DAO: ";
            logger.error(message + e);
            throw new DaoException(message, e);
        }
        return isLogIn;
    }
}
