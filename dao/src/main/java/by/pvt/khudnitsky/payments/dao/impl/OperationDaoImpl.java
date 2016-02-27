/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import java.util.List;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.IOperationDao;
import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.dto.OperationDTO;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */

@Repository
public class OperationDaoImpl extends AbstractDao<Operation> implements IOperationDao{
    private static Logger logger = Logger.getLogger(OperationDaoImpl.class);
    private String message;
    private final String DELETE_BY_ACCOUNT_ID = "delete from Operation as o where o.account.id = :accountId";
    private final String GET_OPERATIONS = "select o.F_DATE as operationDate, " +
                                        "o.F_DESCRIPTION as description, " +
                                        "o.F_AMOUNT as amount, " +
                                        "u.F_LASTNAME as userLastName, " +
                                        "a.F_ACCOUNTNUMBER as accountNumber " +
                                        "from t_operation as o " +
                                        "inner join t_user as u " +
                                        "on o.F_USER_ID=u.F_ID " +
                                        "inner join t_account as a " +
                                        "on o.F_ACCOUNT_ID=a.F_ID ";

    @Autowired
    private OperationDaoImpl(SessionFactory sessionFactory){
        super(Operation.class, sessionFactory);
    }

    @Override
    public void deleteByAccountId(Long id) throws DaoException {
        try {
            Session session = getCurrentSession();
            Query query = session.createQuery(DELETE_BY_ACCOUNT_ID);
            query.setParameter("accountId", id);
            query.executeUpdate();
        }
        catch(HibernateException e){
            message = "Unable to delete the operation. Error was thrown in DAO: ";
            logger.error(message + e);
            throw new DaoException(message, e);
        }
    }

    public List<OperationDTO> getOperations(int recordsPerPage, int pageNumber, String sorting) throws DaoException {
        List<OperationDTO> list;
        try {
            Session session = getCurrentSession();
            SQLQuery query = session.createSQLQuery(GET_OPERATIONS + sorting);
            query.addScalar("operationDate", StandardBasicTypes.STRING);
            query.addScalar("description", StandardBasicTypes.STRING);
            query.addScalar("amount", StandardBasicTypes.DOUBLE);
            query.addScalar("userLastName", StandardBasicTypes.STRING);
            query.addScalar("accountNumber", StandardBasicTypes.LONG);
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
            message = "Unable to get list of operations. Error was thrown in DAO: ";
            logger.error(message + e);
            throw new DaoException(message, e);
        }
        return list;
    }
}

