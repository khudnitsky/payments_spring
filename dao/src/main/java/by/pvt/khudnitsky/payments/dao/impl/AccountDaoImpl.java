/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IAccountDao;
import by.pvt.khudnitsky.payments.dao.constants.Constants;
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
    private Class persistenceClass = Account.class;

    @Autowired
    private AccountDaoImpl(SessionFactory sessionFactory){
        super(Account.class, sessionFactory);
    }

    @Override
    public boolean isAccountStatusBlocked(Long id) throws DaoException {
        boolean isBlocked = false;
        try {
            Session session = getCurrentSession();
            Criteria criteria = session.createCriteria(persistenceClass);
            criteria.add(Restrictions.eq(Constants.PARAMETER_ID, id));
            criteria.add(Restrictions.eq(Constants.PARAMETER_ACCOUNT_STATUS, AccountStatusType.BLOCKED));
            if(criteria.uniqueResult() != null){
                isBlocked = true;
            }
        }
        catch(HibernateException e){
            logger.error(Constants.ERROR_ACCOUNT_STATUS + e);
            throw new DaoException(Constants.ERROR_ACCOUNT_STATUS, e);
        }
        return isBlocked;
    }

    @Override
    public List<Account> getBlockedAccounts() throws DaoException {
        List<Account> list;
        try {
            Session session = getCurrentSession();
            Criteria criteria = session.createCriteria(persistenceClass);
            criteria.add(Restrictions.eq(Constants.PARAMETER_ACCOUNT_STATUS, AccountStatusType.BLOCKED));
            list = criteria.list();
        }
        catch(HibernateException e){
            logger.error(Constants.ERROR_BLOCKED_ACCOUNT_LIST + e);
            throw new DaoException(Constants.ERROR_BLOCKED_ACCOUNT_LIST, e);
        }
        return list;
    }

    @Override
    public Account getByNumber(Long accountNumber) throws DaoException {
        Account account = null;
        try {
            Session session = getCurrentSession();
            Criteria criteria = session.createCriteria(persistenceClass);
            criteria.add(Restrictions.eq(Constants.PARAMETER_ACCOUNT_NUMBER, accountNumber));
            if(criteria.uniqueResult() != null){
                account = (Account) criteria.uniqueResult();
            }
        }
        catch(HibernateException e){
            logger.error(Constants.ERROR_USER_BY_LOGIN + e);
            throw new DaoException(Constants.ERROR_USER_BY_LOGIN, e);
        }
        return account;
    }
}