package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.pojos.Account;
import by.pvt.khudnitsky.payments.pojos.User;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;

import java.util.List;

/**
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 19:59
 */
public interface IAccountService extends IService<Account>{
    List<Account> getBlockedAccounts() throws ServiceException;
    void updateAccountStatus(Long accountNumber, AccountStatusType status) throws ServiceException;
    boolean checkAccountStatus(Long id) throws ServiceException;
    void addFunds(User user, String description, Double amount) throws ServiceException;
    void blockAccount(User user, String description) throws ServiceException;
    void payment(User user, String description, Double amount) throws ServiceException;
}
