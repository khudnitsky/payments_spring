package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.impl.UserDetailDaoImpl;
import by.pvt.khudnitsky.payments.entities.UserDetail;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.IUserDetailService;
import by.pvt.khudnitsky.payments.utils.TransactionUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

/**
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 20:29
 */
public class UserDetailServiceImpl extends AbstractService<UserDetail> implements IUserDetailService {
    private static Logger logger = Logger.getLogger(UserDetailServiceImpl.class);
    private static UserDetailServiceImpl instance;
    private UserDetailDaoImpl userDetailDao = UserDetailDaoImpl.getInstance();

    private UserDetailServiceImpl(){}

    public static synchronized UserDetailServiceImpl getInstance(){
        if(instance == null){
            instance = new UserDetailServiceImpl();
        }
        return instance;
    }

    @Override
    public Serializable save(UserDetail entity) throws ServiceException {
        Serializable id;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            id = userDetailDao.save(entity);
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
    public List<UserDetail> getAll() throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserDetail getById(Long id) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(UserDetail entity) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) throws ServiceException {
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            userDetailDao.delete(id);
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info("Deleted user detail #" + id);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
    }
}
