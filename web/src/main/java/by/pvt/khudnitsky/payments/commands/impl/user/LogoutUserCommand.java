/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.user;

import javax.servlet.http.HttpServletRequest;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.enums.PagePath;
import by.pvt.khudnitsky.payments.managers.PagePathManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class LogoutUserCommand extends AbstractCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = PagePathManager.getInstance().getProperty(PagePath.HOME_PAGE_PATH);
        request.getSession().invalidate();
        return page;
    }
}
