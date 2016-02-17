/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.user;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.enums.*;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.managers.MessageManager;
import by.pvt.khudnitsky.payments.services.impl.UserServiceImpl;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class LoginUserCommand extends AbstractCommand {
    private User user;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        try {
            user = RequestParameterParser.getUser(request);
            if(UserServiceImpl.getInstance().checkUserAuthorization(user.getLogin(), user.getPassword())){
                user = UserServiceImpl.getInstance().getUserByLogin(user.getLogin());
                AccessLevelType accessLevelType = UserServiceImpl.getInstance().checkAccessLevel(user);
                session.setAttribute(Parameters.USER, user);
                session.setAttribute(Parameters.USERTYPE, accessLevelType);
                if(AccessLevelType.CLIENT.equals(accessLevelType)){
                    page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_PAGE_PATH);
                }
                else{
                    page = ConfigurationManager.getInstance().getProperty(PagePath.ADMIN_PAGE_PATH);
                }
            }
            else{
                page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_LOGIN_OR_PASSWORD, MessageManager.getInstance().getProperty(MessageConstants.WRONG_LOGIN_OR_PASSWORD));
            }
        }
        catch (ServiceException e) {
            page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        return page;
    }
}
