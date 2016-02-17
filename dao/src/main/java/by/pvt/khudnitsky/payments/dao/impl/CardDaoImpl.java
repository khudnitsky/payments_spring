/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.ICardDao;
import by.pvt.khudnitsky.payments.entities.Card;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class CardDaoImpl extends AbstractDao<Card> implements ICardDao{
    private static CardDaoImpl instance;

    private CardDaoImpl(){
        super(Card.class);
    }

    public static synchronized CardDaoImpl getInstance(){
        if(instance == null){
            instance = new CardDaoImpl();
        }
        return instance;
    }
}
