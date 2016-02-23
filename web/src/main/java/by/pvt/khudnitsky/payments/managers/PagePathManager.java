/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.managers;

import java.util.ResourceBundle;

import by.pvt.khudnitsky.payments.enums.ConfigConstant;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class PagePathManager {
    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigConstant.CONFIGS_SOURCE);
    private static PagePathManager instance;

    private PagePathManager(){}

    public static synchronized PagePathManager getInstance(){
        if(instance == null){
            instance = new PagePathManager();
        }
        return instance;
    }

    public String getProperty(String key){
        return bundle.getString(key);
    }
}
