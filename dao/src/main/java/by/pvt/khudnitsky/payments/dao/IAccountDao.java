package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.pojos.Account;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

import java.util.List;

/**
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 18:43
 */
public interface IAccountDao extends IDao<Account> {
    boolean isAccountStatusBlocked(Long id) throws DaoException;
    List<Account> getBlockedAccounts() throws DaoException;
    Account getByNumber(Long accountNumber) throws DaoException;
}
