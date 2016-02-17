package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.impl.CurrencyDaoImpl;
import by.pvt.khudnitsky.payments.entities.Currency;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.ICurrencyService;
import by.pvt.khudnitsky.payments.utils.TransactionUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

/**
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 20:28
 */
public class CurrencyServiceImpl extends AbstractService<Currency> implements ICurrencyService {
    private static Logger logger = Logger.getLogger(CurrencyServiceImpl.class);
    private static CurrencyServiceImpl instance;

    private CurrencyDaoImpl currencyDao = CurrencyDaoImpl.getInstance();

    private CurrencyServiceImpl(){}

    public static synchronized CurrencyServiceImpl getInstance(){
        if(instance == null){
            instance = new CurrencyServiceImpl();
        }
        return instance;
    }

    @Override
    public Serializable save(Currency entity) throws ServiceException {
        Serializable id;
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            id = currencyDao.save(entity);
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
    public List<Currency> getAll() throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Currency getById(Long id) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Currency entity) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) throws ServiceException {
        Session session = util.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            currencyDao.delete(id);
            transaction.commit();
            logger.info(TRANSACTION_SUCCEEDED);
            logger.info("Deleted currency #" + id);
        }
        catch (DaoException e) {
            TransactionUtil.rollback(transaction, e);
            logger.error(TRANSACTION_FAILED, e);
            throw new ServiceException(TRANSACTION_FAILED + e);
        }
    }
}
