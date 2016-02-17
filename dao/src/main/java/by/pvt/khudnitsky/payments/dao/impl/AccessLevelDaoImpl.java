package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IAccessLevelDao;
import by.pvt.khudnitsky.payments.entities.AccessLevel;

/**
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 19:43
 */
public class AccessLevelDaoImpl extends AbstractDao<AccessLevel> implements IAccessLevelDao {
    private static AccessLevelDaoImpl instance;

    private AccessLevelDaoImpl(){
        super(AccessLevel.class);
    }

    /**
     * Singleton method
     * @return entity of <b>AccessLevelDaoImpl</b>
     */
    public static synchronized AccessLevelDaoImpl getInstance(){
        if(instance == null){
            instance = new AccessLevelDaoImpl();
        }
        return instance;
    }
}
