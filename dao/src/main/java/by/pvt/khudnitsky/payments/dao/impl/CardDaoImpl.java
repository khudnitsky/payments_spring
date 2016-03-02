/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.AbstractDao;
import by.pvt.khudnitsky.payments.dao.ICardDao;
import by.pvt.khudnitsky.payments.pojos.Card;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
@Repository
public class CardDaoImpl extends AbstractDao<Card> implements ICardDao{

    @Autowired
    private CardDaoImpl(SessionFactory sessionFactory){
        super(Card.class, sessionFactory);
    }
}
