package by.pvt.khudnitsky.payments.controllers;

import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.enums.MessageConstants;
import by.pvt.khudnitsky.payments.enums.PagePath;
import by.pvt.khudnitsky.payments.enums.Parameters;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.MessageManager;
import by.pvt.khudnitsky.payments.managers.PagePathManager;
import by.pvt.khudnitsky.payments.services.IAccountService;
import by.pvt.khudnitsky.payments.services.impl.AccountServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by: khudnitsky
 * Date: 23.02.2016
 * Time: 14:14
 */
@Component
@RequestMapping(value = "/client")
public class ClientController {
    private IAccountService accountService = AccountServiceImpl.getInstance();

    private PagePathManager pagePathManager = PagePathManager.getInstance();
    private MessageManager messageManager = MessageManager.getInstance();

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String showClientMainPage(){
        return pagePathManager.getProperty(PagePath.CLIENT_PAGE_PATH);
    }

    @RequestMapping(value = "/balance", method = GET)
    public String showBalance(ModelMap model,
                              @RequestParam(value = Parameters.USER) User user,
                              HttpServletRequest request) {
        String pagePath;
        AccessLevelType accessLevelType = (AccessLevelType) model.get(Parameters.USER_ACCESS_LEVEL);
        if(accessLevelType == AccessLevelType.CLIENT){
            try {
                // TODO DTO
                Set<Account> accounts = user.getAccounts();
                Iterator<Account> iterator = accounts.iterator();
                Long accountId = -1L;
                while (iterator.hasNext()){
                    accountId = iterator.next().getId();
                }
                Account account = accountService.getById(accountId);
                model.addAttribute(Parameters.OPERATION_BALANCE, account.getDeposit());
                model.addAttribute(Parameters.ACCOUNT_CURRENCY, account.getCurrency().getCurrencyType());
                pagePath = pagePathManager.getProperty(PagePath.CLIENT_BALANCE_PAGE_PATH);
            }
            catch (ServiceException e) {
                model.addAttribute(Parameters.ERROR_DATABASE, messageManager.getProperty(MessageConstants.ERROR_DATABASE));
                pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
            }
        }
        else{
            pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            request.getSession().invalidate();
        }
        return pagePath;
    }

    @RequestMapping(value = "/funds", method = GET)
    public String showFundsPage(){
        return pagePathManager.getProperty(PagePath.CLIENT_FUND_PAGE_PATH);
    }

    @RequestMapping(value = "funds/add", method = POST)
    public String addFund(ModelMap model,
                          @RequestParam(Parameters.USER_ACCESS_LEVEL) AccessLevelType accessLevelType,
                          @RequestParam(Parameters.USER) User user,
                          @RequestParam(Parameters.OPERATION_ADD_FUNDS) double amount,
                          HttpServletRequest request) {
        String pagePath;
        if(accessLevelType == AccessLevelType.CLIENT) {
            try {
                // TODO DTO
                Set<Account> accounts = user.getAccounts();
                Iterator<Account> iterator = accounts.iterator();
                Long accountId = -1L;
                while (iterator.hasNext()){
                    accountId = iterator.next().getId();
                }
                if (!accountService.checkAccountStatus(accountId)) {
                    if (amount > 0) {
                        String description = "Платеж"; // TODO вынести
                        accountService.addFunds(user, description, amount);
                        model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.SUCCESS_OPERATION));
                        pagePath = pagePathManager.getProperty(PagePath.CLIENT_FUND_PAGE_PATH);
                    } else {
                        model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.NEGATIVE_ARGUMENT));
                        pagePath = pagePathManager.getProperty(PagePath.CLIENT_FUND_PAGE_PATH);
                    }
                } else {
                    pagePath = pagePathManager.getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
                }
            }
            catch (ServiceException e) {
                model.addAttribute(Parameters.ERROR_DATABASE, messageManager.getProperty(MessageConstants.ERROR_DATABASE));
                pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
            } catch (NumberFormatException e) {
                model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.INVALID_NUMBER_FORMAT));
                pagePath = pagePathManager.getProperty(PagePath.CLIENT_FUND_PAGE_PATH);
            }
        }
        else{
            pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            request.getSession().invalidate();
        }
        return pagePath;
    }

    @RequestMapping(value = "/block", method = GET)
    public String blockAccount(ModelMap model,
                               @RequestParam(Parameters.USER_ACCESS_LEVEL) AccessLevelType accessLevelType,
                               @RequestParam(Parameters.USER) User user,
                               HttpServletRequest request) {
        String pagePath;
        if(accessLevelType == AccessLevelType.CLIENT){
            String description = "Блокировка счета";   // TODO
            try {
                // TODO DTO
                Set<Account> accounts = user.getAccounts();
                Iterator<Account> iterator = accounts.iterator();
                Long accountId = -1L;
                while (iterator.hasNext()){
                    accountId = iterator.next().getId();
                }
                if(!accountService.checkAccountStatus(accountId)){
                    accountService.blockAccount(user, description);
                    model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.SUCCESS_OPERATION));
                    pagePath = pagePathManager.getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
                }
                else{
                    pagePath = pagePathManager.getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
                }
            }
            catch (ServiceException e) {
                model.addAttribute(Parameters.ERROR_DATABASE, messageManager.getProperty(MessageConstants.ERROR_DATABASE));
                pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
            }
        }
        else{
            pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            request.getSession().invalidate();
        }
        return pagePath;
    }

    @RequestMapping(value = "/payments", method = GET)
    public String showPaymentsPage(){
        return pagePathManager.getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
    }

    @RequestMapping(value = "/payments/payment", method = POST)
    public String payment(ModelMap model,
                          @RequestParam(Parameters.USER_ACCESS_LEVEL) AccessLevelType accessLevelType,
                          @RequestParam(Parameters.USER) User user,
                          @RequestParam(Parameters.OPERATION_PAYMENT) double amount,
                          HttpServletRequest request) {
        String pagePath;
        if(accessLevelType == AccessLevelType.CLIENT){
            try {
                // TODO DTO
                Set<Account> accounts = user.getAccounts();
                Iterator<Account> iterator = accounts.iterator();
                Long accountId = -1L;
                while (iterator.hasNext()){
                    accountId = iterator.next().getId();
                }
                if(!AccountServiceImpl.getInstance().checkAccountStatus(accountId)){
                    if(amount > 0){
                        Account account = accountService.getById(accountId);
                        if(account.getDeposit() >= amount){
                            String description = "Платеж"; // TODO
                            accountService.payment(user, description, amount);
                            model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.SUCCESS_OPERATION));
                            pagePath = pagePathManager.getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
                        }
                        else{
                            model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.FAILED_OPERATION));
                            pagePath = pagePathManager.getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
                        }
                    }
                    else{
                        model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.NEGATIVE_ARGUMENT));
                        pagePath = pagePathManager.getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
                    }
                }
                else{
                    pagePath = pagePathManager.getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
                }
            }
            catch (ServiceException e) {
                model.addAttribute(Parameters.ERROR_DATABASE, messageManager.getProperty(MessageConstants.ERROR_DATABASE));
                pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
            }
            catch (NumberFormatException e){
                model.addAttribute(Parameters.OPERATION_MESSAGE, messageManager.getProperty(MessageConstants.INVALID_NUMBER_FORMAT));
                pagePath = messageManager.getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
            }
        }
        else{
            pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            request.getSession().invalidate();
        }
        return pagePath;
    }
}
