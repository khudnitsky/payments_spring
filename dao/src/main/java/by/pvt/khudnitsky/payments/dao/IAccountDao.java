package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.entities.Account;
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
//    void updateAmount(Long id, Double amount) throws DaoException;
//    void updateAccountStatus(Long id, Integer status) throws DaoException;
}
