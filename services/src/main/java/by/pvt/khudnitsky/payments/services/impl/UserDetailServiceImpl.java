package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.IUserDetailDao;
import by.pvt.khudnitsky.payments.pojos.UserDetail;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.IUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * NOT USED
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 20:29
 */

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DaoException.class)
public class UserDetailServiceImpl extends AbstractService<UserDetail> implements IUserDetailService {

    @Autowired
    public UserDetailServiceImpl(IUserDetailDao userDetailDao){
        super(userDetailDao);
    }
}
