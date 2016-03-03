package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.pojos.AccessLevel;

/**
 * Describes interface IAccessLevelService
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 20:00
 */
public interface IAccessLevelService extends IService<AccessLevel> {
    /**
     * Gets access level by access level type
     * @param accessLevelType
     * @return
     * @throws ServiceException
     */
    AccessLevel getByAccessLevelType(AccessLevelType accessLevelType) throws ServiceException;
}
