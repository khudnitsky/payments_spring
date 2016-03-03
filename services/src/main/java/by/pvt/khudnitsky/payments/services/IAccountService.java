package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.pojos.Account;
import by.pvt.khudnitsky.payments.pojos.User;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;

import java.util.List;

/**
 * Describes interface IService
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 19:59
 */
public interface IAccountService extends IService<Account>{
    /**
     * Gets account by it's number
     * @param accountNumber account number
     * @return account
     * @throws ServiceException
     */
    Account getByAccountNumber(Long accountNumber) throws ServiceException;

    /**
     * Gets list of blocked accounts
     * @return list of blocked accounts
     * @throws ServiceException
     */
    List<Account> getBlockedAccounts() throws ServiceException;

    /**
     * Updates account status
     * @param accountNumber - account number
     * @param status - account status
     * @throws ServiceException
     */
    void updateAccountStatus(Long accountNumber, AccountStatusType status) throws ServiceException;

    /**
     * Checks account status
     * @param id - account id
     * @return true - if account blocked, false - otherwise
     * @throws ServiceException
     */
    boolean checkAccountStatus(Long id) throws ServiceException;

    /**
     * Adds funds on account
     * @param user - user
     * @param description - operation description
     * @param amount - amount
     * @throws ServiceException
     */
    void addFunds(User user, String description, Double amount) throws ServiceException;

    /**
     * Blocks account
     * @param user - user
     * @param description - operation description
     * @throws ServiceException
     */
    void blockAccount(User user, String description) throws ServiceException;

    /**
     * Payment operation
     * @param user - user
     * @param description - operation description
     * @param amount - amount
     * @throws ServiceException
     */
    void payment(User user, String description, Double amount) throws ServiceException;
}
