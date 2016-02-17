package by.pvt.khudnitsky.payments.exceptions;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class DaoException extends Exception {
    public DaoException() {
        super();
    }

    public DaoException(String message){
        super(message);
    }

    public DaoException(String message, Throwable cause){
        super(message, cause);
    }

}
