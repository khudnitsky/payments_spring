package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.pojos.AccessLevel;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

/**
 * Describes the interface <b>IAccessLevelDao</b>
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 19:42
 */
public interface IAccessLevelDao extends IDao<AccessLevel> {
    /**
     * Gets access level type
     * @param accessLevelType - access level type
     * @return access level type
     * @throws DaoException
     */
    AccessLevel getByAccessLevelType(AccessLevelType accessLevelType) throws DaoException;
}
