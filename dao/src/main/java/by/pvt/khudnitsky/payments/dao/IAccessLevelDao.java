package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.pojos.AccessLevel;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

/**
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 19:42
 */
public interface IAccessLevelDao extends IDao<AccessLevel> {
    AccessLevel getByAccessLevelType(AccessLevelType accessLevelType) throws DaoException;
}
