/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao;

import by.pvt.khudnitsky.payments.pojos.AbstractEntity;
import by.pvt.khudnitsky.payments.exceptions.DaoException;

import java.io.Serializable;
import java.util.List;

/**
 * Describes the interface <b>IDao</b>
 * @author khudnitsky
 * @version 1.0
 */
public interface IDao<T extends AbstractEntity> {
    /**
     * Saves the entity
     * @param entity - entity of AbstractEntity inheritors
     * @return id of persist entity
     * @throws DaoException
     */
    Serializable save(T entity) throws DaoException;

    /**
     * Finds all entities from persistence storage
     * @return list of AbstractEntity inheritors
     * @throws DaoException
     */
    List<T> getAll() throws DaoException;

    /**
     * Finds entity by it's id in persistence storage
     * @param id - identification of entity in persistence storage
     * @return entity
     * @throws DaoException
     */
    T getById(Long id) throws DaoException;

    /**
     * Updates entity in persistence storage
     * @param entity - entity of AbstractEntity inheritors
     * @throws DaoException
     */
    void update(T entity) throws DaoException;

    /**
     * Deletes entity in persistence storage
     * @param id - identification of entity in persistence storage
     * @throws DaoException
     */
    void delete(Long id) throws DaoException;

    /**
     * Gets the amount of stored entities
     * Used for pagination
     * @return the amount of stored entities
     * @throws DaoException
     */
    Long getAmount() throws DaoException;

    /**
     * Get list of entities, that needed to display on page at the moment
     * Used for pagination
     * @param recordsPerPage - the amount of entities, that needed to display on page at the moment
     * @param pageNumber - number of page
     * @return list of AbstractEntity inheritors
     * @throws DaoException
     */
    List<T> getAllToPage(int recordsPerPage, int pageNumber) throws DaoException;
}
