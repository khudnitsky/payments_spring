package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.pojos.Account;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

import java.util.List;

/**
 * Describes the interface <b>IAccountDao</b>
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 18:43
 */
public interface IAccountDao extends IDao<Account> {
    /**
     * Checks is account status blocked
     * @param id - entity's id
     * @return true - if account blocked, false - otherwise
     * @throws DaoException
     */
    boolean isAccountStatusBlocked(Long id) throws DaoException;

    /**
     * Gets list of blocked accounts
     * @return list of blocked accounts
     * @throws DaoException
     */
    List<Account> getBlockedAccounts() throws DaoException;

    /**
     * Gets account by number
     * @param accountNumber - account's number
     * @return account
     * @throws DaoException
     */
    Account getByNumber(Long accountNumber) throws DaoException;
}
