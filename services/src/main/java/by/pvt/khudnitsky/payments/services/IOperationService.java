package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.entities.Operation;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;

/**
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 19:59
 */
public interface IOperationService extends IService<Operation> {
    void deleteByAccountId(Long id)throws ServiceException;
}
