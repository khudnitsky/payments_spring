package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IUserDetailDao;
import by.pvt.khudnitsky.payments.entities.UserDetail;

/**
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 1:02
 */
public class UserDetailDaoImpl extends AbstractDao<UserDetail> implements IUserDetailDao {
    private static UserDetailDaoImpl instance;

    private UserDetailDaoImpl(){
        super(UserDetail.class);
    }

    public static synchronized UserDetailDaoImpl getInstance(){
        if(instance == null){
            instance = new UserDetailDaoImpl();
        }
        return instance;
    }
}
