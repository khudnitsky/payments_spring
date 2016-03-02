/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IAccountDao;
import by.pvt.khudnitsky.payments.pojos.Account;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is responsible to get data from a data source which can be
 * database / xml or any other storage mechanism.
 * @author khudnitsky
 * @version 1.0
 *
 */

@Repository
public class AccountDaoImpl extends AbstractDao<Account> implements IAccountDao{
    private static Logger logger = Logger.getLogger(AccountDaoImpl.class);
    private String message;

    @Autowired
    private AccountDaoImpl(SessionFactory sessionFactory){
        super(Account.class, sessionFactory);
    }

    /**
     * Checks if account is blocked
     * @param id - account's id
     * @return true - if account is blocked, false - otherwise
     * @throws DaoException
     */
    @Override
    public boolean isAccountStatusBlocked(Long id) throws DaoException {
        boolean isBlocked = false;
        try {
            Session session = getCurrentSession();
            Criteria criteria = session.createCriteria(Account.class);
            criteria.add(Restrictions.eq("id", id));
            criteria.add(Restrictions.eq("accountStatus", AccountStatusType.BLOCKED));
            if(criteria.uniqueResult() != null){
                isBlocked = true;
            }
        }
        catch(HibernateException e){
            message = "Unable to check account status. Error was thrown in DAO: ";
            logger.error(message + e);
            throw new DaoException(message, e);
        }
        return isBlocked;
    }

    /**
     * Gets list of blocked accounts from the storage
     * @return list of blocked accounts
     * @throws DaoException
     */
    @Override
    public List<Account> getBlockedAccounts() throws DaoException {
        List<Account> list;
        try {
            Session session = getCurrentSession();
            Criteria criteria = session.createCriteria(Account.class);
            criteria.add(Restrictions.eq("accountStatus", AccountStatusType.BLOCKED));
            list = criteria.list();
        }
        catch(HibernateException e){
            message = "Unable to return list of blocked accounts. Error was thrown in DAO: ";
            logger.error(message + e);
            throw new DaoException(message, e);
        }
        return list;
    }

    @Override
    public Account getByNumber(Long accountNumber) throws DaoException {
        Account account = null;
        try {
            Session session = getCurrentSession();
            Criteria criteria = session.createCriteria(Account.class);
            criteria.add(Restrictions.eq("accountNumber", accountNumber));
            if(criteria.uniqueResult() != null){
                account = (Account) criteria.uniqueResult();
            }
        }
        catch(HibernateException e){
            message = "Unable to return user by login. Error was thrown in DAO: ";
            logger.error(message + e);
            throw new DaoException(message, e);
        }
        return account;
    }
}