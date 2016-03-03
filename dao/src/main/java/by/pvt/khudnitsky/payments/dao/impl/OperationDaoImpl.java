/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import java.util.List;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IOperationDao;
import by.pvt.khudnitsky.payments.dao.constants.Constants;
import by.pvt.khudnitsky.payments.pojos.Operation;
import by.pvt.khudnitsky.payments.dto.OperationDTO;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementation of IOperationDao interface
 * @author khudnitsky
 * @version 1.0
 */

@Repository
public class OperationDaoImpl extends AbstractDao<Operation> implements IOperationDao{
    private static Logger logger = Logger.getLogger(OperationDaoImpl.class);

    @Autowired
    private OperationDaoImpl(SessionFactory sessionFactory){
        super(Operation.class, sessionFactory);
    }

    @Override
    public void deleteByAccountId(Long id) throws DaoException {
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery(Constants.HQL_DELETE_BY_ACCOUNT_ID);
            query.setParameter(Constants.PARAMETER_ACCOUNT_ID, id);
            query.executeUpdate();
        }
        catch(HibernateException e){
            logger.error(Constants.ERROR_OPERATION_DELETE + e);
            throw new DaoException(Constants.ERROR_OPERATION_DELETE, e);
        }
    }

    @Override
    public List<OperationDTO> getOperations(int recordsPerPage, int pageNumber, String sorting) throws DaoException {
        List<OperationDTO> list;
        try {
            Session session = getCurrentSession();
            SQLQuery query = session.createSQLQuery(Constants.SQL_GET_OPERATIONS + sorting);
            query.addScalar(Constants.PARAMETER_OPERATION_DATE, StandardBasicTypes.STRING);
            query.addScalar(Constants.PARAMETER_OPERATION_DESCRIPTION, StandardBasicTypes.STRING);
            query.addScalar(Constants.PARAMETER_OPERATION_AMOUNT, StandardBasicTypes.DOUBLE);
            query.addScalar(Constants.PARAMETER_USER_LAST_NAME, StandardBasicTypes.STRING);
            query.addScalar(Constants.PARAMETER_ACCOUNT_NUMBER, StandardBasicTypes.LONG);
            query.setResultTransformer(new ResultTransformer() {
                @Override
                public Object transformTuple(Object[] tuple, String[] aliases) {
                    OperationDTO operation = new OperationDTO();
                    operation.setOperationDate((String)tuple[0]);
                    operation.setDescription((String)tuple[1]);
                    operation.setAmount((Double)tuple[2]);
                    operation.setUserLastName((String)tuple[3]);
                    operation.setAccountNumber((Long)tuple[4]);
                    return operation;
                }

                @Override
                public List transformList(List collection) {
                    return collection;
                }
            });
            query.setFirstResult((pageNumber - 1) * recordsPerPage);
            query.setMaxResults(recordsPerPage);
            list = query.list();
        } catch (HibernateException e) {
            logger.error(Constants.ERROR_OPERATIONS_LIST + e);
            throw new DaoException(Constants.ERROR_OPERATIONS_LIST, e);
        }
        return list;
    }
}

