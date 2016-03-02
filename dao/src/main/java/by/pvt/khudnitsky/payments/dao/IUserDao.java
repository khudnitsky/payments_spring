package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.pojos.User;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

/**
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 18:56
 */
public interface IUserDao extends IDao<User> {
    User getByLogin(String login) throws DaoException;
    boolean isAuthorized(String login, String password) throws DaoException;
}
