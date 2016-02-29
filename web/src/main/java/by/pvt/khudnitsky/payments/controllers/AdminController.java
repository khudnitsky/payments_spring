package by.pvt.khudnitsky.payments.controllers;

import by.pvt.khudnitsky.payments.dto.OperationDTO;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.enums.*;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.PagePathManager;
import by.pvt.khudnitsky.payments.services.IAccountService;
import by.pvt.khudnitsky.payments.services.IOperationService;
import by.pvt.khudnitsky.payments.services.IUserService;
import by.pvt.khudnitsky.payments.utils.PaginationFilterUtil;
import by.pvt.khudnitsky.payments.utils.PaginationFilter;
import by.pvt.khudnitsky.payments.utils.OrderingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

/**
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

    @ModelAttribute("filter")
    public PaginationFilter createFilter(){
        return new PaginationFilter();
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String showAdminMainPage(){
        return pagePathManager.getProperty(PagePath.ADMIN_PAGE_PATH);
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String showClients(ModelMap model,
                              Locale locale,
                              HttpSession session) {
        String pagePath;

        //TODO исправить
        AccessLevelType accessLevelType = (AccessLevelType) session.getAttribute(Parameters.USERTYPE);
//        AccessLevelType accessLevelType = (AccessLevelType) model.get(Parameters.USER_ACCESS_LEVEL);
        if(accessLevelType == AccessLevelType.ADMINISTRATOR){
            try{
                List<User> list = userService.getAll();
                model.addAttribute(Parameters.USER_LIST, list);
                pagePath = pagePathManager.getProperty(PagePath.ADMIN_SHOW_CLIENTS_PAGE);
            }
            catch (ServiceException e) {
                model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
                pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
            }
        }
        // TODO ПРОВверить, возможно отработает фильтр
        else{
            pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            session.invalidate();
        }
        return pagePath;
    }

    @RequestMapping(value = "/operations", method = {RequestMethod.GET, RequestMethod.POST})
    public String showOperations(ModelMap model,
                                 Locale locale,
                                 /*@RequestParam(value = Parameters.CURRENT_PAGE, required = false) Integer currentPage,
                                 @RequestParam(value = Parameters.RECORDS_PER_PAGE, required = false) Integer recordsPerPage,
                                 @RequestParam(value = Parameters.ORDERING, required = false) String ordering,*/
                                 @ModelAttribute ("filter") PaginationFilter filter,
                                 HttpSession session) {
        String pagePath;
        Integer currentPage = filter.getCurrentPage();
        Integer recordsPerPage = filter.getRecordsPerPage();
        String ordering = filter.getOrdering();
        filter = PaginationFilterUtil.defineParameters(ordering, null, currentPage, recordsPerPage);
        AccessLevelType accessLevelType = (AccessLevelType) session.getAttribute(Parameters.USERTYPE);
        if(accessLevelType == AccessLevelType.ADMINISTRATOR){
            try{
                int numberOfPages = operationService.getNumberOfPages(filter.getRecordsPerPage());
                List<OperationDTO> list = operationService.getAllToPage(filter.getRecordsPerPage(), filter.getCurrentPage(), OrderingUtil.defineOrderingType(filter.getOrdering()));
                model.addAttribute(Parameters.OPERATIONS_LIST, list);
                model.addAttribute(Parameters.NUMBER_OF_PAGES, numberOfPages);
//                model.addAttribute(Parameters.CURRENT_PAGE, filter.getCurrentPage());
//                model.addAttribute(Parameters.RECORDS_PER_PAGE, filter.getRecordsPerPage());
//                model.addAttribute(Parameters.ORDERING, ordering);
                model.addAttribute("filter", filter);
                pagePath = /*"redirect:" +*/ pagePathManager.getProperty(PagePath.ADMIN_SHOW_OPERATIONS_PAGE);
            }
            catch (ServiceException e) {
                model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
                pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
            }
        }
        else{
            pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            session.invalidate();
        }
        return pagePath;
    }

    @RequestMapping(value = "/unblock", method = RequestMethod.GET)
    public String showUnblockPage(ModelMap model,
                                  Locale locale,
                                  HttpSession session){
        String pagePath;
        AccessLevelType accessLevelType = (AccessLevelType) session.getAttribute(Parameters.USERTYPE);
        if(accessLevelType == AccessLevelType.ADMINISTRATOR){
            try {
                List<Account> list = accountService.getBlockedAccounts();
                model.addAttribute(Parameters.ACCOUNTS_LIST, list);
                pagePath = pagePathManager.getProperty(PagePath.ADMIN_UNBLOCK_PAGE);
            }
            catch (ServiceException e) {
                model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
                pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
            }
        }
        else{
            pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            session.invalidate();
        }
        return pagePath;
    }

    @RequestMapping(value = "/unblock", method = RequestMethod.POST)
    public String unblockAccount(ModelMap model,
                                 @RequestParam(value = Parameters.OPERATION_UNBLOCK) Long accountNumber,
                                 @RequestParam(value = Parameters.ACCOUNTS_LIST) List<Account> accountList,
                                 Locale locale,
                                 HttpSession session){
        String pagePath;
        AccessLevelType accessLevelType = (AccessLevelType) session.getAttribute(Parameters.USERTYPE);
        if(accessLevelType == AccessLevelType.ADMINISTRATOR){
            try{
                accountService.updateAccountStatus(accountNumber, AccountStatusType.UNBLOCKED);
                accountList = accountService.getBlockedAccounts();
                model.addAttribute(Parameters.ACCOUNTS_LIST, accountList);
                pagePath = pagePathManager.getProperty(PagePath.ADMIN_UNBLOCK_PAGE);
            }
            catch(NumberFormatException e){
                if(!accountList.isEmpty()){
                    model.addAttribute(Parameters.ERROR_EMPTY_CHOICE, messageSource.getMessage("message.emptychoice", null, locale));
                    pagePath = pagePathManager.getProperty(PagePath.ADMIN_UNBLOCK_PAGE);
                }
                else{
                    model.addAttribute(Parameters.ERROR_EMPTY_LIST, messageSource.getMessage("message.emptylist", null, locale));
                    pagePath = pagePathManager.getProperty(PagePath.ADMIN_UNBLOCK_PAGE);
                }
            }
            catch (ServiceException e) {
                model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
                pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
            }
        }
        else{
            pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            session.invalidate();
        }
        return pagePath;
    }
}
