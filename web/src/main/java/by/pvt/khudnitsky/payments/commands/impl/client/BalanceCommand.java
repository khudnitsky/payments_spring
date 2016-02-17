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
import by.pvt.khudnitsky.payments.enums.*;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.MessageManager;
import by.pvt.khudnitsky.payments.services.impl.AccountServiceImpl;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class BalanceCommand extends AbstractCommand {
    private User user;

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        AccessLevelType accessLevelType = RequestParameterParser.getUserType(request);
        if(accessLevelType == AccessLevelType.CLIENT){
            user = RequestParameterParser.getRecordUser(request);
            try {
                // TODO DTO
                Set<Account> accounts = user.getAccounts();
                Iterator<Account> iterator = accounts.iterator();
                Long accountId = -1L;
                while (iterator.hasNext()){
                    accountId = iterator.next().getId();
                }
                Account account = AccountServiceImpl.getInstance().getById(accountId);
                request.setAttribute(Parameters.OPERATION_BALANCE, account.getDeposit());
                request.setAttribute(Parameters.ACCOUNT_CURRENCY, account.getCurrency().getCurrencyType());
                page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_BALANCE_PAGE_PATH);
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
