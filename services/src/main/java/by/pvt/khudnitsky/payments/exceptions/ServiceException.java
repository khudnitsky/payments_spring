package by.pvt.khudnitsky.payments.exceptions;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }



    public ServiceException(String message){
        super(message);
    }

    public ServiceException(String message, Throwable cause){
        super(message, cause);
    }

}
