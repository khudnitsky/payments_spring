package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IAccessLevelDao;
import by.pvt.khudnitsky.payments.entities.AccessLevel;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 19:43
 */

@Repository
public class AccessLevelDaoImpl extends AbstractDao<AccessLevel> implements IAccessLevelDao {

    @Autowired
    private AccessLevelDaoImpl(SessionFactory sessionFactory){
        super(AccessLevel.class, sessionFactory);
    }

}
