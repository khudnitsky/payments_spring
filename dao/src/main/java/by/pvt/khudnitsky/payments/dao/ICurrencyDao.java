package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.pojos.Currency;
import by.pvt.khudnitsky.payments.enums.CurrencyType;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

/**
 * Describes the interface <b>ICurrencyDao</b>
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 19:42
 */
public interface ICurrencyDao extends IDao<Currency> {
    /**
     * Gets currency type
     * @param currencyType currency type
     * @return currency type
     * @throws DaoException
     */
    Currency getByCurrencyType(CurrencyType currencyType) throws DaoException;
}
