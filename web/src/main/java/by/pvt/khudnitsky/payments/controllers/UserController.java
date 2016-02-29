package by.pvt.khudnitsky.payments.controllers;

import by.pvt.khudnitsky.payments.dto.UserDTO;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.Currency;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.enums.*;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.managers.PagePathManager;
import by.pvt.khudnitsky.payments.services.IUserService;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by: khudnitsky
 * Date: 22.02.2016
 * Time: 11:39
 */

@Controller
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private PagePathManager pagePathManager;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/home" , method = RequestMethod.GET)
    public String showHomePage(){
        return pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(@RequestParam(Parameters.USER_LOGIN) String login,
                            @RequestParam(Parameters.USER_PASSWORD) String password,
                            Locale locale,
                            ModelMap model,
                            HttpSession session) {
        String pagePath;
        try {
            if(userService.checkUserAuthorization(login, password)){
                User user = userService.getUserByLogin(login);
                AccessLevelType accessLevelType = userService.checkAccessLevel(user);
                session.setAttribute(Parameters.USER, user);
                session.setAttribute(Parameters.USERTYPE, accessLevelType);       // TODO УБрать, там уже есть user
                if(AccessLevelType.CLIENT.equals(accessLevelType)){
                    pagePath = pagePathManager.getProperty(PagePath.CLIENT_PAGE_PATH);
                }
                else{
                    pagePath = pagePathManager.getProperty(PagePath.ADMIN_PAGE_PATH);
                }
            }
            else{
                model.addAttribute(Parameters.WRONG_LOGIN_OR_PASSWORD, messageSource.getMessage("message.loginerror", null, locale));
                pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            }
        }
        catch (ServiceException e) {
            model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
            pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
        }
        return pagePath;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutUser(HttpSession session) {
        String pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
        session.invalidate();
        return pagePath;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(ModelMap model){
        model.addAttribute(Parameters.NEW_USER, new UserDTO());
        return pagePathManager.getProperty(PagePath.REGISTRATION_PAGE_PATH);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrateUser(@ModelAttribute("newUser") @Valid UserDTO userDTO,
                                 Locale locale,
                                 BindingResult bindingResult,
                                 ModelMap model) {
        String pagePath;
        if(!bindingResult.hasErrors()) {
            try {
                // TODO Transformer from DTO to Entity
                User user = EntityBuilder.buildUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getLogin(), userDTO.getPassword_1(), null, null, null);
                Currency currency = EntityBuilder.buildCurrency(CurrencyType.valueOf(userDTO.getCurrency()));
                Account account = EntityBuilder.buildAccount(userDTO.getAccountNumber(), 0D, AccountStatusType.UNBLOCKED, currency, user);
                user.addAccount(account);
                user.addAccessLevel(EntityBuilder.buildAccessLevel(AccessLevelType.CLIENT));

                if (userService.checkIsNewUser(user.getLogin())) {
                    userService.bookUser(user, account);
                    model.addAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage("message.successoperation", null, locale));
                    pagePath = pagePathManager.getProperty(PagePath.REGISTRATION_PAGE_PATH);
                } else {
                    model.addAttribute(Parameters.ERROR_USER_EXISTS, messageSource.getMessage("message.userexsistserror", null, locale));
                    pagePath = pagePathManager.getProperty(PagePath.REGISTRATION_PAGE_PATH);
                }
            }
            catch (ServiceException e) {
                model.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
                pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
            }
            catch (NullPointerException e) {  // TODO исправить
                pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
            }
        }
        else{
            pagePath = pagePathManager.getProperty(PagePath.REGISTRATION_PAGE_PATH);
        }
        return pagePath;
    }
}
