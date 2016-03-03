/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import java.util.List;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IUserDao;
import by.pvt.khudnitsky.payments.dao.constants.Constants;
import by.pvt.khudnitsky.payments.pojos.User;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementation of IUserDao interface
 * @author khudnitsky
 * @version 1.0
 *
 */

@Repository
public class UserDaoImpl extends AbstractDao<User> implements IUserDao{
    private static Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Autowired
    private UserDaoImpl(SessionFactory sessionFactory){
        super(User.class, sessionFactory);
    }

    @Override
    public List<User> getAll() throws DaoException {
        List<User> results;
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery(Constants.HQL_GET_ALL_CLIENTS);
            query.setCacheable(true);
            //query.setParameter("accessLevelType", AccessLevelType.CLIENT);
            results = query.list();
        }
        catch(HibernateException e){
            logger.error(Constants.ERROR_USERS_LIST + e);
            throw new DaoException(Constants.ERROR_USERS_LIST, e);
        }
        return results;
    }

    @Override
    public User getByLogin(String login) throws DaoException {
        User user;
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery(Constants.HQL_GET_BY_LOGIN);
            query.setParameter(Constants.PARAMETER_USER_LOGIN, login);
            user = (User) query.uniqueResult();
        }
        catch(HibernateException e){
            logger.error(Constants.ERROR_USER_BY_LOGIN + e);
            throw new DaoException(Constants.ERROR_USER_BY_LOGIN, e);
        }
        return user;
    }
}
