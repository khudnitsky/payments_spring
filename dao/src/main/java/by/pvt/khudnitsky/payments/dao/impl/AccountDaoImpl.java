/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IAccountDao;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * This class is responsible to get data from a data source which can be
 * database / xml or any other storage mechanism.
 * @author khudnitsky
 * @version 1.0
 *
 */
public class AccountDaoImpl extends AbstractDao<Account> implements IAccountDao{
    private static Logger logger = Logger.getLogger(AccountDaoImpl.class);
    private static AccountDaoImpl instance;
    static String message;

    private AccountDaoImpl(){
        super(Account.class);
    }

    /**
     * Singleton method
     * @return entity of <b>AccountDaoImpl</b>
     */
    public static synchronized AccountDaoImpl getInstance(){
        if(instance == null){
            instance = new AccountDaoImpl();
        }
        return instance;
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
            Session session = util.getSession();
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
            Session session = util.getSession();
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
}