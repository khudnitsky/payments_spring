package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.ICurrencyDao;
import by.pvt.khudnitsky.payments.entities.AccessLevel;
import by.pvt.khudnitsky.payments.entities.Currency;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
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
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 19:45
 */

@Repository
public class CurrencyDaoImpl extends AbstractDao<Currency> implements ICurrencyDao {
    private static Logger logger = Logger.getLogger(CurrencyDaoImpl.class);
    private String message;
    private final String GET_BY_CURRENCY_TYPE = "from Currency where currencyType = :currencyType";

    @Autowired
    private CurrencyDaoImpl(SessionFactory sessionFactory){
        super(Currency.class, sessionFactory);
    }

    @Override
    public Currency getByCurrencyType(CurrencyType currencyType) throws DaoException {
        Currency currency;
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery(GET_BY_CURRENCY_TYPE);
            query.setParameter("currencyType", currencyType);
            currency = (Currency) query.uniqueResult();
        }
        catch(HibernateException e){
            message = "Unable to return user by login. Error was thrown in DAO: ";
            logger.error(message + e);
            throw new DaoException(message, e);
        }
        return currency;
    }
}
