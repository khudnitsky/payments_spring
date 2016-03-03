package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.constants.ServiceConstants;
import by.pvt.khudnitsky.payments.dao.IAccessLevelDao;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.pojos.AccessLevel;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.IAccessLevelService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Implementation of interface IAccessLevelService
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 20:06
 */

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DaoException.class)
public class AccessLevelServiceImpl extends AbstractService<AccessLevel> implements IAccessLevelService {
    private static Logger logger = Logger.getLogger(AccessLevelServiceImpl.class);

    @Autowired
    private IAccessLevelDao accessLevelDao;

    @Autowired
    public AccessLevelServiceImpl(IAccessLevelDao accessLevelDao){
        super(accessLevelDao);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AccessLevel getByAccessLevelType(AccessLevelType accessLevelType) throws ServiceException {
        AccessLevel accessLevel;
        try {
            accessLevel = accessLevelDao.getByAccessLevelType(accessLevelType);
        }
        catch (DaoException e) {
            logger.error(ServiceConstants.TRANSACTION_FAILED, e);
            throw new ServiceException(ServiceConstants.TRANSACTION_FAILED + e);
        }
        return accessLevel;
    }
}
