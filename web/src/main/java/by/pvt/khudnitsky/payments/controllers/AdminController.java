package by.pvt.khudnitsky.payments.controllers;

import by.pvt.khudnitsky.payments.dto.OperationDTO;
import by.pvt.khudnitsky.payments.pojos.Account;
import by.pvt.khudnitsky.payments.pojos.User;
import by.pvt.khudnitsky.payments.enums.*;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.PagePathManager;
import by.pvt.khudnitsky.payments.services.IAccountService;
import by.pvt.khudnitsky.payments.services.IOperationService;
import by.pvt.khudnitsky.payments.services.IUserService;
import by.pvt.khudnitsky.payments.utils.PaginationFilterUtil;
import by.pvt.khudnitsky.payments.utils.PaginationFilter;
import by.pvt.khudnitsky.payments.utils.OrderingUtil;
import by.pvt.khudnitsky.payments.utils.PrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

/**
 * Admin Controller
 * Created by: khudnitsky
 * Date: 23.02.2016
 * Time: 14:13
 */

@Controller
@RequestMapping("/admin")
@SessionAttributes("filter")
public class AdminController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IOperationService operationService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private PagePathManager pagePathManager;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private PrincipalUtil principalUtil;

    @ModelAttribute("filter")
    public PaginationFilter createFilter(){
        return new PaginationFilter();
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String showAdminMainPage(ModelMap model){
        model.addAttribute(Parameters.USER, principalUtil.getPrincipal());
        return pagePathManager.getProperty(PagePath.ADMIN_PAGE_PATH);
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String showClients(ModelMap model,
                              Locale locale) {
        String pagePath;
        try {
            List<User> list = userService.getAll();
            model.addAttribute(Parameters.USER_LIST, list);
            pagePath = pagePathManager.getProperty(PagePath.ADMIN_SHOW_CLIENTS_PAGE);
        }
        catch (ServiceException e) {
            model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
            pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
        }
        return pagePath;
    }

    @RequestMapping(value = "/operations", method = {RequestMethod.GET, RequestMethod.POST})
    public String showOperations(ModelMap model,
                                 Locale locale,
                                 @ModelAttribute ("filter") PaginationFilter filter) {
        String pagePath;
        Integer currentPage = filter.getCurrentPage();
        Integer recordsPerPage = filter.getRecordsPerPage();
        String ordering = filter.getOrdering();
        String direction = filter.getDirection();
        filter = PaginationFilterUtil.defineParameters(ordering, direction, currentPage, recordsPerPage);

        try {
            int numberOfPages = operationService.getNumberOfPages(filter.getRecordsPerPage());
            String order = OrderingUtil.defineOrderingType(filter.getOrdering()) + OrderingUtil.defineOrderingDirection(filter.getDirection());
            List<OperationDTO> list = operationService.getAllToPage(filter.getRecordsPerPage(), filter.getCurrentPage(), order);
            model.addAttribute(Parameters.OPERATIONS_LIST, list);
            model.addAttribute(Parameters.NUMBER_OF_PAGES, numberOfPages);
            model.addAttribute(Parameters.FILTER, filter);
            pagePath = /*"redirect:" +*/ pagePathManager.getProperty(PagePath.ADMIN_SHOW_OPERATIONS_PAGE);
        }
        catch (ServiceException e) {
            model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
            pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
        }
        return pagePath;
    }

    @RequestMapping(value = "/unblock", method = RequestMethod.GET)
    public String showUnblockPage(ModelMap model,
                                  Locale locale){
        String pagePath;
        try {
            List<Account> list = accountService.getBlockedAccounts();
            model.addAttribute(Parameters.ACCOUNTS_LIST, list);
            pagePath = pagePathManager.getProperty(PagePath.ADMIN_UNBLOCK_PAGE);
        }
        catch (ServiceException e) {
            model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
            pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
        }
        return pagePath;
    }

    @RequestMapping(value = "/unblock", method = RequestMethod.POST)
    public String unblockAccount(ModelMap model,
                                 @RequestParam(value = Parameters.OPERATION_UNBLOCK, required = false) Long accountNumber,
                                 Locale locale){
        String pagePath;
        try {
            if(accountNumber != null) {
                accountService.updateAccountStatus(accountNumber, AccountStatusType.UNBLOCKED);
            }
            else {
                model.addAttribute(Parameters.ERROR_EMPTY_CHOICE, messageSource.getMessage("message.emptychoice", null, locale));
            }

            List<Account> accountList = accountService.getBlockedAccounts();

            if (accountList.isEmpty()) {
                model.addAttribute(Parameters.ERROR_EMPTY_LIST, messageSource.getMessage("message.emptylist", null, locale));
            }
            else{
                model.addAttribute(Parameters.ACCOUNTS_LIST, accountList);
            }

            pagePath = pagePathManager.getProperty(PagePath.ADMIN_UNBLOCK_PAGE);
        }
        catch (ServiceException e) {
            model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
            pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
        }
        return pagePath;
    }
}
