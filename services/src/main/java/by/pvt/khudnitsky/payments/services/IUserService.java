package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;

/**
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 15:35
 */
public interface IUserService extends IService<User>{
    boolean checkUserAuthorization(String login, String password) throws ServiceException;
    User getUserByLogin(String login) throws ServiceException;
    AccessLevelType checkAccessLevel(User user) throws ServiceException;
    boolean checkIsNewUser(User user) throws ServiceException;
    void bookUser(User user, Account account) throws ServiceException;
}
