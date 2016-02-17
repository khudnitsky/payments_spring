package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.impl.AccessLevelDaoImpl;
import by.pvt.khudnitsky.payments.dao.impl.AccountDaoImpl;
import by.pvt.khudnitsky.payments.dao.impl.UserDaoImpl;
import by.pvt.khudnitsky.payments.entities.AccessLevel;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.IAccessLevelService;
import by.pvt.khudnitsky.payments.utils.TransactionUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

/**
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 20:06
 */
public class AccessLevelServiceImpl extends AbstractService<AccessLevel> implements IAccessLevelService {
    private static Logger logger = Logger.getLogger(AccessLevelServiceImpl.class);
    private static AccessLevelServiceImpl instance;
    private AccessLevelDaoImpl accessLevelDao = AccessLevelDaoImpl.getInstance();

    private AccessLevelServiceImpl(){}

    public static synchronized AccessLevelServiceImpl getInstance(){
        if(instance == null){
            instance = new AccessLevelServiceImpl();
        }
        return instance;
    }

    @Override
    public Serializable save(AccessLevel entity) throws ServiceException {
        Serializable id;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            id = accessLevelDao.save(entity);
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

    @Override
    public List<AccessLevel> getAll() throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public AccessLevel getById(Long id) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(AccessLevel entity) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) throws ServiceException {
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            accessLevelDao.delete(id);
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info("Deleted access level #" + id);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
    }
}
