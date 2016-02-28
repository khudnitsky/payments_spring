package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.ICurrencyDao;
import by.pvt.khudnitsky.payments.dao.impl.CurrencyDaoImpl;
import by.pvt.khudnitsky.payments.entities.Currency;
import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.AbstractService;
import by.pvt.khudnitsky.payments.services.ICurrencyService;
import by.pvt.khudnitsky.payments.utils.TransactionUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 20:28
 */

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DaoException.class)
public class CurrencyServiceImpl extends AbstractService<Currency> implements ICurrencyService {

    @Autowired
    public CurrencyServiceImpl(ICurrencyDao currencyDao){
        super(currencyDao);
    }
}
