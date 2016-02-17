package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.entities.Card;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.ICardService;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class CardServiceImpl extends AbstractService<Card> implements ICardService{
    private static CardServiceImpl instance;

    private CardServiceImpl(){}

    public static synchronized CardServiceImpl getInstance(){
        if(instance == null){
            instance = new CardServiceImpl();
        }
        return instance;
    }

    /**
     * Calls Dao save() method
     *
     * @param entity - object of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public Serializable save(Card entity) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao getAll() method
     *
     * @return list of objects of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public List<Card> getAll() throws ServiceException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao getById() method
     *
     * @param id - id of entity
     * @return object of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public Card getById(Long id) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao update() method
     *
     * @param entity - object of derived class AbstractEntity
     * @throws SQLException
     */
    @Override
    public void update(Card entity) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    /**
     * Calls Dao delete() method
     *
     * @param id - id of entity
     * @throws SQLException
     */
    @Override
    public void delete(Long id) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}
