package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.ICurrencyDao;
import by.pvt.khudnitsky.payments.entities.Currency;

/**
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 19:45
 */
public class CurrencyDaoImpl extends AbstractDao<Currency> implements ICurrencyDao {
    private static CurrencyDaoImpl instance;

    private CurrencyDaoImpl(){
        super(Currency.class);
    }

    /**
     * Singleton method
     * @return entity of <b>AccessLevelDaoImpl</b>
     */
    public static synchronized CurrencyDaoImpl getInstance(){
        if(instance == null){
            instance = new CurrencyDaoImpl();
        }
        return instance;
    }
}
