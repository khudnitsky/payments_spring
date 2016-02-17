package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

/**
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 18:56
 */
public interface IOperationDao extends IDao<Operation>{
    void deleteByAccountId(Long id) throws DaoException;
}
