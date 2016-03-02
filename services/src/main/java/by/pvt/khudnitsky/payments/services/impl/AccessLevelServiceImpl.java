package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.IAccessLevelDao;
import by.pvt.khudnitsky.payments.pojos.AccessLevel;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.IAccessLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 20:06
 */

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DaoException.class)
public class AccessLevelServiceImpl extends AbstractService<AccessLevel> implements IAccessLevelService {

    @Autowired
    public AccessLevelServiceImpl(IAccessLevelDao accessLevelDao){
        super(accessLevelDao);
    }
}
