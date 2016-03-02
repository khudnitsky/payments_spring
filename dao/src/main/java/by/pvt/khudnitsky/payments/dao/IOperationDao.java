package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.dto.OperationDTO;
import by.pvt.khudnitsky.payments.pojos.Operation;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

import java.util.List;

/**
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 18:56
 */
public interface IOperationDao extends IDao<Operation>{
    void deleteByAccountId(Long id) throws DaoException;
    List<OperationDTO> getOperations(int recordsPerPage, int pageNumber, String sorting) throws DaoException; // TODO getAllToPage
}
