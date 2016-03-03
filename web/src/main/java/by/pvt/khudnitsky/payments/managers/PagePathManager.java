/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.managers;

import java.util.ResourceBundle;

import by.pvt.khudnitsky.payments.enums.ConfigConstant;
import org.springframework.stereotype.Component;

/**
 * PathPageManager
 * @author khudnitsky
 * @version 1.0
 *
 */

@Component
public class PagePathManager {
    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigConstant.CONFIGS_SOURCE);

    private PagePathManager(){}

    public String getProperty(String key){
        return bundle.getString(key);
    }
}
