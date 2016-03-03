package by.pvt.khudnitsky.payments.utils;

import org.hibernate.cfg.DefaultNamingStrategy;

/**
 * Utilite class, used foe automatic naming of tables and columns in DB
 * Created by: khudnitsky
 * Date: 05.02.2016
 * Time: 16:22
 */
public class CustomNamingStrategy extends DefaultNamingStrategy {
    @Override
    public String classToTableName(String className) {
        return "T_" + super.classToTableName(className).toUpperCase();
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        return "F_" + super.propertyToColumnName(propertyName).toUpperCase();
    }
}