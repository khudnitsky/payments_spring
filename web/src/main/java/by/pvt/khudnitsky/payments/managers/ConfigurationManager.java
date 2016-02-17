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
public class ConfigurationManager {
    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigConstant.CONFIGS_SOURCE);
    private static ConfigurationManager instance;

    private ConfigurationManager(){}

    public static synchronized ConfigurationManager getInstance(){
        if(instance == null){
            instance = new ConfigurationManager();
        }
        return instance;
    }

    public String getProperty(String key){
        return bundle.getString(key);
    }
}
