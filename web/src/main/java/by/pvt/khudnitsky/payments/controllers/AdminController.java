package by.pvt.khudnitsky.payments.controllers;

import by.pvt.khudnitsky.payments.dto.OperationDTO;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.enums.*;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.MessageManager;
import by.pvt.khudnitsky.payments.managers.PagePathManager;
import by.pvt.khudnitsky.payments.services.IAccountService;
import by.pvt.khudnitsky.payments.services.IOperationService;
import by.pvt.khudnitsky.payments.services.IUserService;
import by.pvt.khudnitsky.payments.services.impl.AccountServiceImpl;
import by.pvt.khudnitsky.payments.services.impl.OperationServiceImpl;
import by.pvt.khudnitsky.payments.services.impl.UserServiceImpl;
import by.pvt.khudnitsky.payments.utils.FilterUtil;
import by.pvt.khudnitsky.payments.utils.PaginationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by: khudnitsky
 * Date: 23.02.2016
 * Time: 14:13
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
    private IUserService userService = UserServiceImpl.getInstance();
    private IOperationService operationService = OperationServiceImpl.getInstance();
    private IAccountService accountService = AccountServiceImpl.getInstance();
    private PagePathManager pagePathManager = PagePathManager.getInstance();
    private MessageManager messageManager = MessageManager.getInstance();

    // todo НАДО?
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String showAdminMainPage(ModelMap model){
        return pagePathManager.getProperty(PagePath.ADMIN_PAGE_PATH);
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String showClients(ModelMap model, HttpServletRequest request) {
        String pagePath;

        //TODO исправить
        AccessLevelType accessLevelType = (AccessLevelType) model.get(Parameters.USER_ACCESS_LEVEL);
        if(accessLevelType == AccessLevelType.ADMINISTRATOR){
            try{
                List<User> list = userService.getAll();
                model.put(Parameters.USER_LIST, list);
                pagePath = pagePathManager.getProperty(PagePath.ADMIN_SHOW_CLIENTS_PAGE);
            }
            catch (ServiceException e) {
                pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
                model.put(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
            }
        }
        // TODO ПРОВверить, возможно отработает фильтр
        else{
            pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            request.getSession().invalidate();   //TODO
        }
        return pagePath;
    }

    @RequestMapping(value = "/operations", method = RequestMethod.GET)
    public String showOperations(ModelMap model,
                                 @RequestParam(value = Parameters.CURRENT_PAGE) int currentPage,
                                 @RequestParam(value = Parameters.RECORDS_PER_PAGE) int recordsPerPage,
                                 @RequestParam(value = Parameters.ORDERING) String ordering,
                                 HttpServletRequest request) {
        String pagePath;
        PaginationFilter filter = FilterUtil.defineParameters(ordering, null, currentPage, recordsPerPage);
        AccessLevelType accessLevelType = (AccessLevelType) model.get(Parameters.USERTYPE);
        if(accessLevelType == AccessLevelType.ADMINISTRATOR){
            try{
                int numberOfPages = operationService.getNumberOfPages(recordsPerPage);
                List<OperationDTO> list = operationService.getAllToPage(recordsPerPage, currentPage, ordering);
                model.put(Parameters.OPERATIONS_LIST, list);
                model.put(Parameters.NUMBER_OF_PAGES, numberOfPages);
                model.put(Parameters.CURRENT_PAGE, filter.getCurrentPage());
                model.put(Parameters.RECORDS_PER_PAGE, filter.getRecordsPerPage());
                model.put(Parameters.ORDERING, filter.getOrdering());
                pagePath = pagePathManager.getProperty(PagePath.ADMIN_SHOW_OPERATIONS_PAGE);
            }
            catch (ServiceException e) {
                pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
                model.put(Parameters.ERROR_DATABASE, messageManager.getProperty(MessageConstants.ERROR_DATABASE));
            }
        }
        else{
            pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            request.getSession().invalidate();   // TODO
        }
        return pagePath;
    }

    @RequestMapping(value = "/unblock", method = RequestMethod.GET)
    public String showUnblockPage(ModelMap model,
                                  HttpServletRequest request){
        String pagePath;
        AccessLevelType accessLevelType = (AccessLevelType) model.get(Parameters.USER_ACCESS_LEVEL);
        if(accessLevelType == AccessLevelType.ADMINISTRATOR){
            try {
                List<Account> list = accountService.getBlockedAccounts();
                model.put(Parameters.ACCOUNTS_LIST, list);
                pagePath = pagePathManager.getProperty(PagePath.ADMIN_UNBLOCK_PAGE);
            }
            catch (ServiceException e) {
                pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
                model.put(Parameters.ERROR_DATABASE, messageManager.getProperty(MessageConstants.ERROR_DATABASE));
            }
        }
        else{
            pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            request.getSession().invalidate();  // TODO
        }
        return pagePath;
    }

    @RequestMapping(value = "/unblockAccount", method = RequestMethod.POST)
    public String unblockAccount(ModelMap model,
                                 @RequestParam(value = Parameters.OPERATION_UNBLOCK) Long accountNumber,
                                 @RequestParam(value = Parameters.ACCOUNTS_LIST) List<Account> accountList,
                                 HttpServletRequest request){
        String pagePath;
        AccessLevelType accessLevelType = (AccessLevelType) model.get(Parameters.USERTYPE);
        if(accessLevelType == AccessLevelType.ADMINISTRATOR){
            try{
                accountService.updateAccountStatus(accountNumber, AccountStatusType.UNBLOCKED);
                accountList = accountService.getBlockedAccounts();
                model.put(Parameters.ACCOUNTS_LIST, accountList);
                pagePath = pagePathManager.getProperty(PagePath.ADMIN_UNBLOCK_PAGE);
            }
            catch(NumberFormatException e){
                if(!accountList.isEmpty()){
                    model.put(Parameters.ERROR_EMPTY_CHOICE, messageManager.getProperty(MessageConstants.EMPTY_CHOICE));
                    pagePath = pagePathManager.getProperty(PagePath.ADMIN_UNBLOCK_PAGE);
                }
                else{
                    model.put(Parameters.ERROR_EMPTY_LIST, messageManager.getProperty(MessageConstants.EMPTY_LIST));
                    pagePath = pagePathManager.getProperty(PagePath.ADMIN_UNBLOCK_PAGE);
                }
            }
            catch (ServiceException e) {
                model.put(Parameters.ERROR_DATABASE, messageManager.getProperty(MessageConstants.ERROR_DATABASE));
                pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
            }
        }
        else{
            pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            request.getSession().invalidate();
        }
        return pagePath;
    }
}
