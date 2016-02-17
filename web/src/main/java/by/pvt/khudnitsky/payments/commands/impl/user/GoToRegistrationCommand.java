/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.user;

import javax.servlet.http.HttpServletRequest;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.enums.PagePath;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class GoToRegistrationCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(PagePath.REGISTRATION_PAGE_PATH);
        return page;
    }
}
