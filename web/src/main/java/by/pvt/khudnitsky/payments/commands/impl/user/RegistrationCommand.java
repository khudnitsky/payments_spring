/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.user;

import javax.servlet.http.HttpServletRequest;

import by.pvt.khudnitsky.payments.enums.PagePath;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.impl.UserServiceImpl;
import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.enums.MessageConstants;
import by.pvt.khudnitsky.payments.enums.Parameters;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.managers.MessageManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class RegistrationCommand extends AbstractCommand {
    private User user;
    private Account account;
    private String accountIdString;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        try{
            user = RequestParameterParser.getUser(request);
            accountIdString = request.getParameter(Parameters.ACCOUNT_NUMBER);
            //if(areFieldsFullStocked()){
                account = RequestParameterParser.getAccount(request);
                if(UserServiceImpl.getInstance().checkIsNewUser(user)){
                    UserServiceImpl.getInstance().bookUser(user, account);
                    page = ConfigurationManager.getInstance().getProperty(PagePath.REGISTRATION_PAGE_PATH);
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
                }
                else{
                    page = ConfigurationManager.getInstance().getProperty(PagePath.REGISTRATION_PAGE_PATH);
                    request.setAttribute(Parameters.ERROR_USER_EXISTS, MessageManager.getInstance().getProperty(MessageConstants.USER_EXISTS));
                }
            //}
//            else{
//                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.EMPTY_FIELDS));
//                page = ConfigurationManager.getInstance().getProperty(PagePath.REGISTRATION_PAGE_PATH);
//            }
        }
        catch (ServiceException e) {
            page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        catch (NumberFormatException e) {
            request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.INVALID_NUMBER_FORMAT));
            page = ConfigurationManager.getInstance().getProperty(PagePath.REGISTRATION_PAGE_PATH);
        }
        // TODO исправить
        catch(NullPointerException e){
            page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
        }
        return page;
    }

//    // TODO javascript???
//    private boolean areFieldsFullStocked(){
//
//        boolean isFullStocked = false;
//        if(!user.getFirstName().isEmpty()
//                & !user.getLastName().isEmpty()
//                & !user.getLogin().isEmpty()
//                & !user.getPassword().isEmpty()
//                & !accountIdString.isEmpty()){
//            isFullStocked = true;
//        }
//        return isFullStocked;
//    }
}
