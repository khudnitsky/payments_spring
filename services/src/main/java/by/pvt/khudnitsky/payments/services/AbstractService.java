package by.pvt.khudnitsky.payments.services;

import by.pvt.khudnitsky.payments.entities.AbstractEntity;
import by.pvt.khudnitsky.payments.utils.HibernateUtil;

import java.sql.Connection;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public abstract class AbstractService<T extends AbstractEntity> implements IService<T>{
    protected static HibernateUtil util = HibernateUtil.getInstance();
    protected final String TRANSACTION_SUCCEEDED = "Transaction succeeded";
    protected final String TRANSACTION_FAILED = "Error was thrown in service: ";
}
