package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.ICardDao;
import by.pvt.khudnitsky.payments.entities.Card;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */

@Service
public class CardServiceImpl extends AbstractService<Card> implements ICardService{

    @Autowired
    private CardServiceImpl(ICardDao cardDao){
        super(cardDao);
    }
}
