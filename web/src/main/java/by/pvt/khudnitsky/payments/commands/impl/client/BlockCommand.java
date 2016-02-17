/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.client;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.enums.*;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.managers.MessageManager;
import by.pvt.khudnitsky.payments.services.impl.AccountServiceImpl;
import by.pvt.khudnitsky.payments.commands.factory.CommandType;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class BlockCommand extends AbstractCommand {
    private User user;
    private String description;
    private CommandType commandType;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        AccessLevelType accessLevelType = RequestParameterParser.getUserType(request);
        if(accessLevelType == AccessLevelType.CLIENT){
            user = RequestParameterParser.getRecordUser(request);
            commandType = RequestParameterParser.getCommandType(request);
            description = commandType.getValue();
            try {
                // TODO DTO
                Set<Account> accounts = user.getAccounts();
                Iterator<Account> iterator = accounts.iterator();
                Long accountId = -1L;
                while (iterator.hasNext()){
                    accountId = iterator.next().getId();
                }
                if(!AccountServiceImpl.getInstance().checkAccountStatus(accountId)){
                    AccountServiceImpl.getInstance().blockAccount(user, description);
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
                    page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
                }
                else{
                    page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
                }
            }
            catch (ServiceException e) {
                page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            }
        }
        else{
            page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
