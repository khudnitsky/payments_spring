package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.ICurrencyDao;
import by.pvt.khudnitsky.payments.entities.Currency;
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

    @Autowired
    private CurrencyDaoImpl(SessionFactory sessionFactory){
        super(Currency.class, sessionFactory);
    }
}
