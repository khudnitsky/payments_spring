/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.managers;

import java.util.ResourceBundle;

import by.pvt.khudnitsky.payments.enums.ConfigConstant;
import org.hibernate.annotations.SortComparator;
import org.springframework.stereotype.Component;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */

@Component
public class MessageManager {
    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigConstant.MESSAGES_SOURCE);

    private MessageManager(){}

    public String getProperty(String key) {
        return bundle.getString(key);
    }
}
