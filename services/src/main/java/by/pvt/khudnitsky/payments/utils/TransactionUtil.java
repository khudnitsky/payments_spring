package by.pvt.khudnitsky.payments.utils;

import by.pvt.khudnitsky.payments.exceptions.DaoException;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

/**
 * Created by: khudnitsky
 * Date: 07.02.2016
 * Time: 21:34
 */
public class TransactionUtil {
    private static Logger logger = Logger.getLogger(TransactionUtil.class);
    private static final String TRANSACTION_ROLLBACK_FAILED = "Error while rollback transaction: ";

    private TransactionUtil(){}

    public static void rollback(Transaction transaction, DaoException e) throws ServiceException {
        if(transaction != null){
            try {
                transaction.rollback();
            }
            catch(HibernateException he){
                logger.error(he);
                throw new ServiceException(TRANSACTION_ROLLBACK_FAILED + e);
            }
        }
    }
}
