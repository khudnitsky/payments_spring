package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.ICurrencyDao;
import by.pvt.khudnitsky.payments.constants.DaoConstants;
import by.pvt.khudnitsky.payments.pojos.Currency;
import by.pvt.khudnitsky.payments.enums.CurrencyType;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementation of ICurrencyDao interface
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 19:45
 */

@Repository
public class CurrencyDaoImpl extends AbstractDao<Currency> implements ICurrencyDao {
    private static Logger logger = Logger.getLogger(CurrencyDaoImpl.class);

    @Autowired
    private CurrencyDaoImpl(SessionFactory sessionFactory){
        super(Currency.class, sessionFactory);
    }

    @Override
    public Currency getByCurrencyType(CurrencyType currencyType) throws DaoException {
        Currency currency;
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery(DaoConstants.HQL_GET_BY_CURRENCY_TYPE);
            query.setParameter(DaoConstants.PARAMETER_CURRENCY_TYPE, currencyType);
            currency = (Currency) query.uniqueResult();
        }
        catch(HibernateException e){
            logger.error(DaoConstants.ERROR_CURRENCY_TYPE + e);
            throw new DaoException(DaoConstants.ERROR_CURRENCY_TYPE, e);
        }
        return currency;
    }
}
