package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.pojos.User;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

/**
 * Describes the interface <b>IUserDao</b>
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 18:56
 */
public interface IUserDao extends IDao<User> {
    /**
     * Gets user by login
     * @param login - user's login
     * @return user
     * @throws DaoException
     */
    User getByLogin(String login) throws DaoException;
}
