package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.dto.OperationDTO;
import by.pvt.khudnitsky.payments.pojos.Operation;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

import java.util.List;

/**
 * Describes the interface <b>IOperationDao</b>
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 18:56
 */
public interface IOperationDao extends IDao<Operation>{
    /**
     * Deletes operation by account's id
     * @param id - account's id
     * @throws DaoException
     */
    void deleteByAccountId(Long id) throws DaoException;

    /**
     * Gets operations
     * Uses for pagination
     * @param recordsPerPage - the amount of operations, that needed to display on page at the moment
     * @param pageNumber - number of page
     * @param sorting - ordering
     * @return list of OperationDTO entities
     * @throws DaoException
     */
    List<OperationDTO> getOperations(int recordsPerPage, int pageNumber, String sorting) throws DaoException;
}
