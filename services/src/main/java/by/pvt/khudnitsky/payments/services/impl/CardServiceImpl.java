package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.ICardDao;
import by.pvt.khudnitsky.payments.pojos.Card;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DaoException.class)
public class CardServiceImpl extends AbstractService<Card> implements ICardService{

    @Autowired
    public CardServiceImpl(ICardDao cardDao){
        super(cardDao);
    }
}
