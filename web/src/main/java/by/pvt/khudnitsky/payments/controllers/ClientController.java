package by.pvt.khudnitsky.payments.controllers;

import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.enums.PagePath;
import by.pvt.khudnitsky.payments.enums.Parameters;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.PagePathManager;
import by.pvt.khudnitsky.payments.services.IAccountService;
import by.pvt.khudnitsky.payments.services.IUserService;
import by.pvt.khudnitsky.payments.utils.PrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpSession;
import java.util.DoubleSummaryStatistics;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/**
 * Created by: khudnitsky
 * Date: 23.02.2016
 * Time: 14:14
 */
@Controller
@RequestMapping(value = "/client")
public class ClientController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private PagePathManager pagePathManager;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private PrincipalUtil principalUtil;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String showClientMainPage(ModelMap model){
        model.addAttribute(Parameters.USER, principalUtil.getPrincipal());
        return pagePathManager.getProperty(PagePath.CLIENT_PAGE_PATH);
    }

    @RequestMapping(value = "/balance", method = GET)
    public String showBalance(ModelMap model,
                              Locale locale) {
        String pagePath;
        try {
            // TODO DTO
            User user = userService.getUserByLogin(principalUtil.getPrincipal().getLogin());
            Set<Account> accounts = user.getAccounts();
            Iterator<Account> iterator = accounts.iterator();
            Long accountId = -1L;
            while (iterator.hasNext()) {
                accountId = iterator.next().getId();
            }
            Account account = accountService.getById(accountId);
            model.addAttribute(Parameters.USER, principalUtil.getPrincipal());
            model.addAttribute(Parameters.OPERATION_BALANCE, account.getDeposit());
            model.addAttribute(Parameters.ACCOUNT_CURRENCY, account.getCurrency().getCurrencyType());
            pagePath = pagePathManager.getProperty(PagePath.CLIENT_BALANCE_PAGE_PATH);
        }
        catch (ServiceException e) {
            model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
            pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
        }
        return pagePath;
    }

    @RequestMapping(value = "/funds", method = GET)
    public String showFundsPage(){
        return pagePathManager.getProperty(PagePath.CLIENT_FUND_PAGE_PATH);
    }

    @RequestMapping(value = "funds", method = POST)
    public String addFund(ModelMap model,
                          @RequestParam(value = Parameters.OPERATION_ADD_FUNDS, required = false) String addFunds,
                          Locale locale) {
        String pagePath;
        try {
            double amount = Double.valueOf(addFunds);
            // TODO DTO
            User user = userService.getUserByLogin(principalUtil.getPrincipal().getLogin());
            Set<Account> accounts = user.getAccounts();
            Iterator<Account> iterator = accounts.iterator();
            Long accountId = -1L;
            while (iterator.hasNext()) {
                accountId = iterator.next().getId();
            }
            if (!accountService.checkAccountStatus(accountId)) {
                if (amount > 0) {
                    String description = "Пополнение счета"; // TODO вынести
                    accountService.addFunds(user, description, amount);
                    model.addAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage("message.successoperation", null, locale));
                    pagePath = pagePathManager.getProperty(PagePath.CLIENT_FUND_PAGE_PATH);
                } else {
                    model.addAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage("message.negativeoperator", null, locale));
                    pagePath = pagePathManager.getProperty(PagePath.CLIENT_FUND_PAGE_PATH);
                }
            } else {
                pagePath = pagePathManager.getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
            }
        } catch (ServiceException e) {
            model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
            pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
        }
        catch(NumberFormatException e){
            model.addAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage("message.invalidnumberformat", null, locale));
            pagePath = pagePathManager.getProperty(PagePath.CLIENT_FUND_PAGE_PATH);
        }
        return pagePath;
    }

    @RequestMapping(value = "/payments", method = GET)
    public String showPaymentsPage(){
        return pagePathManager.getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
    }

    @RequestMapping(value = "/payments", method = POST)
    public String payment(ModelMap model,
                          @RequestParam(value = Parameters.OPERATION_PAYMENT, required = false) String payment,
                          Locale locale) {
        String pagePath;
        try {
            double amount = Double.valueOf(payment);
            // TODO DTO
            User user = userService.getUserByLogin(principalUtil.getPrincipal().getLogin());
            Set<Account> accounts = user.getAccounts();
            Iterator<Account> iterator = accounts.iterator();
            Long accountId = -1L;
            while (iterator.hasNext()) {
                accountId = iterator.next().getId();
            }
            if (!accountService.checkAccountStatus(accountId)) {
                if (amount > 0) {
                    Account account = accountService.getById(accountId);
                    if (account.getDeposit() >= amount) {
                        String description = "Платеж"; // TODO
                        accountService.payment(user, description, amount);
                        model.addAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage("message.successoperation", null, locale));
                        pagePath = pagePathManager.getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
                    } else {
                        model.addAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage("message.failedoperation", null, locale));
                        pagePath = pagePathManager.getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
                    }
                } else {
                    model.addAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage("message.negativeoperator", null, locale));
                    pagePath = pagePathManager.getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
                }
            }
            else {
                pagePath = pagePathManager.getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
            }
        } catch (ServiceException e) {
            model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
            pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
        }
        catch (NumberFormatException e){
            model.addAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage("message.invalidnumberformat", null, locale));
            pagePath = pagePathManager.getProperty(PagePath.CLIENT_PAYMENT_PAGE_PATH);
        }
        return pagePath;
    }

    @RequestMapping(value = "/block", method = GET)
    public String blockAccount(ModelMap model,
                               Locale locale) {
        String pagePath;
        String description = "Блокировка счета";   // TODO
        try {
            // TODO DTO
            User user = userService.getUserByLogin(principalUtil.getPrincipal().getLogin());
            Set<Account> accounts = user.getAccounts();
            Iterator<Account> iterator = accounts.iterator();
            Long accountId = -1L;
            while (iterator.hasNext()) {
                accountId = iterator.next().getId();
            }
            if (!accountService.checkAccountStatus(accountId)) {
                accountService.blockAccount(user, description);
                model.addAttribute(Parameters.USER, principalUtil.getPrincipal());
                model.addAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage("message.successoperation", null, locale));
                pagePath = pagePathManager.getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
            } else {
                pagePath = pagePathManager.getProperty(PagePath.CLIENT_BLOCK_PAGE_PATH);
            }
        } catch (ServiceException e) {
            model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
            pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
        }
        return pagePath;
    }
}
