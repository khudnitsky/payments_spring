package by.pvt.khudnitsky.payments.controllers;

import by.pvt.khudnitsky.payments.dto.UserDTO;
import by.pvt.khudnitsky.payments.entities.AccessLevel;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.Currency;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.enums.*;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.PagePathManager;
import by.pvt.khudnitsky.payments.managers.MessageManager;
import by.pvt.khudnitsky.payments.services.IUserService;
import by.pvt.khudnitsky.payments.services.impl.UserServiceImpl;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by: khudnitsky
 * Date: 22.02.2016
 * Time: 11:39
 */

@Controller
public class UserController {
    private IUserService userService;
    private PagePathManager pagePathManager = PagePathManager.getInstance();
    private MessageManager messageManager = MessageManager.getInstance();
    private User user;

    //@Inject
    public UserController(IUserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String showHomePage(){
        return pagePathManager.getProperty(PagePath.INDEX_PAGE_PATH);
    }

    @RequestMapping(value = "/login_user", method = RequestMethod.POST)
    public String loginUser(@RequestParam(Parameters.USER_LOGIN) String login,
                            @RequestParam(Parameters.USER_PASSWORD) String password,
                            ModelMap model) {
        String pagePath;
        try {
            if(userService.checkUserAuthorization(login, password)){
                user = userService.getUserByLogin(login);
                AccessLevelType accessLevelType = userService.checkAccessLevel(user);
                model.put(Parameters.USER, user);
                model.put(Parameters.USERTYPE, accessLevelType);       // TODO УБрать, там уже есть user
                if(AccessLevelType.CLIENT.equals(accessLevelType)){
                    pagePath = pagePathManager.getProperty(PagePath.CLIENT_PAGE_PATH);
                }
                else{
                    pagePath = pagePathManager.getProperty(PagePath.ADMIN_PAGE_PATH);
                }
            }
            else{
                pagePath = pagePathManager.getProperty(PagePath.INDEX_PAGE_PATH);
                model.put(Parameters.WRONG_LOGIN_OR_PASSWORD, messageManager.getProperty(MessageConstants.WRONG_LOGIN_OR_PASSWORD));
            }
        }
        catch (ServiceException e) {
            pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
            model.put(Parameters.ERROR_DATABASE, messageManager.getProperty(MessageConstants.ERROR_DATABASE));
        }
        return pagePath;
    }

    @RequestMapping(value = "/logout_user", method = RequestMethod.POST)
    public String logoutUser(HttpServletRequest request) {
        String pagePath = pagePathManager.getProperty(PagePath.INDEX_PAGE_PATH);
        request.getSession().invalidate();        // TODO как правильно инвалидировать сессию?
        return pagePath;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(ModelMap model){
        model.put(Parameters.NEW_USER, new UserDTO());
        return pagePathManager.getProperty(PagePath.REGISTRATION_PAGE_PATH);
    }

    @RequestMapping(value = "/registration_add", method = RequestMethod.POST)
    public String registrateUser(UserDTO userDTO, BindingResult bindingResult, ModelMap model) {
        String pagePath;
        try{
            // TODO Transformer from DTO to Entity
            user = EntityBuilder.buildUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLogin(), userDTO.getPassword(), null, null, null);
            Currency currency = EntityBuilder.buildCurrency(CurrencyType.valueOf(userDTO.getCurrency()));
            Account account = EntityBuilder.buildAccount(userDTO.getAccountNumber(), 0D, AccountStatusType.UNBLOCKED, currency, user);
            user.addAccount(account);
            user.addAccessLevel(EntityBuilder.buildAccessLevel(AccessLevelType.CLIENT));

            if(userService.checkIsNewUser(user.getLogin())){
                userService.bookUser(user, account);
                pagePath = pagePathManager.getProperty(PagePath.REGISTRATION_PAGE_PATH);
                model.put(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
            }
            else{
                pagePath = PagePathManager.getInstance().getProperty(PagePath.REGISTRATION_PAGE_PATH);
                model.put(Parameters.ERROR_USER_EXISTS, MessageManager.getInstance().getProperty(MessageConstants.USER_EXISTS));
            }
        }
        catch (ServiceException e) {
            pagePath = PagePathManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
            model.put(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        catch(NullPointerException e){  // TODO исправить
            pagePath = PagePathManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
        }
        return pagePath;
    }
}
