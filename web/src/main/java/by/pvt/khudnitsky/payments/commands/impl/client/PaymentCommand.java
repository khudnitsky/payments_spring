/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.commands.impl.client;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.pvt.khudnitsky.payments.commands.AbstractCommand;
import by.pvt.khudnitsky.payments.enums.*;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.PagePathManager;
import by.pvt.khudnitsky.payments.services.impl.AccountServiceImpl;
import by.pvt.khudnitsky.payments.commands.factory.CommandType;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;
import by.pvt.khudnitsky.payments.managers.MessageManager;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class PaymentCommand extends AbstractCommand {
    private User user;
    private double payment;

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
                if(!AccountServiceImpl.getInstance().checkAccountStatus(accountId)){
                    payment = RequestParameterParser.getAmountFromPayment(request);
                    if(payment > 0){
                        Account account = AccountServiceImpl.getInstance().getById(accountId);
                        if(account.getDeposit() >= payment){
                            CommandType type = RequestParameterParser.getCommandType(request);
                            String description = type.getValue();
                            AccountServiceImpl.getInstance().payment(user, description, payment);
                            request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
                            page = PagePathManager.getInstance().getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
                        }
                        else{
                            request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.FAILED_OPERATION));
                            page = PagePathManager.getInstance().getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
                        }
                    }
                    else{
                        request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.NEGATIVE_ARGUMENT));
                        page = PagePathManager.getInstance().getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
                    }
                }
                else{
                    page = PagePathManager.getInstance().getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
                }
            }
            catch (ServiceException e) {
                page = PagePathManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            }
            catch (NumberFormatException e){
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.INVALID_NUMBER_FORMAT));
                page = PagePathManager.getInstance().getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
            }
        }
        else{
            page = PagePathManager.getInstance().getProperty(PagePath.HOME_PAGE_PATH);
            session.invalidate();
        }
        return page;
    }
}
