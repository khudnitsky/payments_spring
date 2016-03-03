package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IUserDetailDao;
import by.pvt.khudnitsky.payments.pojos.UserDetail;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * NOT USED YET
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 1:02
 */

@Repository
public class UserDetailDaoImpl extends AbstractDao<UserDetail> implements IUserDetailDao {

    @Autowired
    private UserDetailDaoImpl(SessionFactory sessionFactory){
        super(UserDetail.class, sessionFactory);
    }
}
