package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IAccessLevelDao;
import by.pvt.khudnitsky.payments.constants.DaoConstants;
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
 * Implementation of IAccessLevelDao interface
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 19:43
 */

@Repository
public class AccessLevelDaoImpl extends AbstractDao<AccessLevel> implements IAccessLevelDao {
    private static Logger logger = Logger.getLogger(AccessLevelDaoImpl.class);

    @Autowired
    private AccessLevelDaoImpl(SessionFactory sessionFactory){
        super(AccessLevel.class, sessionFactory);
    }

    @Override
    public AccessLevel getByAccessLevelType(AccessLevelType accessLevelType) throws DaoException {
        AccessLevel accessLevel;
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery(DaoConstants.HQL_GET_BY_ACCESS_LEVEL);
            query.setParameter(DaoConstants.PARAMETER_ACCESS_LEVEL_TYPE, accessLevelType);
            accessLevel = (AccessLevel) query.uniqueResult();
        }
        catch(HibernateException e){
            logger.error(DaoConstants.ERROR_ACCESS_LEVEL_TYPE + e);
            throw new DaoException(DaoConstants.ERROR_ACCESS_LEVEL_TYPE, e);
        }
        return accessLevel;
    }
}
