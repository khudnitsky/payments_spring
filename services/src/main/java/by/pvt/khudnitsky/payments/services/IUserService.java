package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.pojos.Account;
import by.pvt.khudnitsky.payments.pojos.User;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;

/**
 * Describes interface IUserService
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 15:35
 */
public interface IUserService extends IService<User>{
    /**
     * Gets ser by login
     * @param login - user login
     * @return user
     * @throws ServiceException
     */
    User getUserByLogin(String login) throws ServiceException;

    /**
     * Checks is user new
     * @param login - user login
     * @param accountNumber - account number
     * @return true - if user is new, false - otherwise
     * @throws ServiceException
     */
    boolean checkIsNewUser(String login, Long accountNumber) throws ServiceException;

    /**
     * Bookes user
     * @param user - user
     * @param account - account
     * @throws ServiceException
     */
    void bookUser(User user, Account account) throws ServiceException;
}
