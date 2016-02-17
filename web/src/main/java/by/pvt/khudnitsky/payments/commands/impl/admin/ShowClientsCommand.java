/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.admin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.enums.*;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.impl.UserServiceImpl;
import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.managers.MessageManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class ShowClientsCommand extends AbstractCommand{

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        AccessLevelType accessLevelType = RequestParameterParser.getUserType(request);
        if(accessLevelType == AccessLevelType.ADMINISTRATOR){
            try{
                List<User> list = UserServiceImpl.getInstance().getAll();
                session.setAttribute(Parameters.USER_LIST, list);
                page = ConfigurationManager.getInstance().getProperty(PagePath.ADMIN_SHOW_CLIENTS_PAGE);
            }
            catch (ServiceException e) {
                page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            }
        }
        // TODO ПРОВверить, возможно отработает фильтр
        else{
            page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
