package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.dto.OperationDTO;
import by.pvt.khudnitsky.payments.pojos.Operation;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;

import java.util.List;

/**
 * Describes interface IOperationService
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 19:59
 */
public interface IOperationService extends IService<Operation> {
    /**
     * Deletes operation by account id
     * @param id - account id
     * @throws ServiceException
     */
    void deleteByAccountId(Long id)throws ServiceException;

    /**
     * Gets number of pages
     * @param recordsPerPage - records per page
     * @return number of pages
     * @throws ServiceException
     */
    int getNumberOfPages(int recordsPerPage) throws ServiceException;

    /**
     * Gets all operation to page
     * @param recordsPerPage - records per page
     * @param pageNumber - page number
     * @param sorting - ordering
     * @return list of operations
     * @throws ServiceException
     */
    List<OperationDTO> getAllToPage(int recordsPerPage, int pageNumber, String sorting) throws ServiceException;
}
