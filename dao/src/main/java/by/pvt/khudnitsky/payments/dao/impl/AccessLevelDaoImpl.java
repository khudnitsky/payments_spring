package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IAccessLevelDao;
import by.pvt.khudnitsky.payments.pojos.AccessLevel;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
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
    private static Logger logger = Logger.getLogger(AccessLevelDaoImpl.class);
    private String message;
    private final String GET_BY_ACCESS_LEVEL = "from AccessLevel where accessLevelType = :accessLevelType";

    @Autowired
    private AccessLevelDaoImpl(SessionFactory sessionFactory){
        super(AccessLevel.class, sessionFactory);
    }

    @Override
    public AccessLevel getByAccessLevelType(AccessLevelType accessLevelType) throws DaoException {
        AccessLevel accessLevel;
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery(GET_BY_ACCESS_LEVEL);
            query.setParameter("accessLevelType", accessLevelType);
            accessLevel = (AccessLevel) query.uniqueResult();
        }
        catch(HibernateException e){
            message = "Unable to return user by login. Error was thrown in DAO: ";
            logger.error(message + e);
            throw new DaoException(message, e);
        }
        return accessLevel;
    }
}
