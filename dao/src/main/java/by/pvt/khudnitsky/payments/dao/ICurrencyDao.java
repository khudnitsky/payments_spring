package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.pojos.Currency;
import by.pvt.khudnitsky.payments.enums.CurrencyType;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

/**
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 19:42
 */
public interface ICurrencyDao extends IDao<Currency> {
    Currency getByCurrencyType(CurrencyType currencyType) throws DaoException;
}
