package by.pvt.khudnitsky.payments.controllers;

import by.pvt.khudnitsky.payments.dto.UserDTO;
import by.pvt.khudnitsky.payments.pojos.Account;
import by.pvt.khudnitsky.payments.pojos.Currency;
import by.pvt.khudnitsky.payments.pojos.User;
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

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutUser() {
        String pagePath = "redirect: " + pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
        return pagePath;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(ModelMap model){
        model.addAttribute(Parameters.NEW_USER, new UserDTO());
        return pagePathManager.getProperty(PagePath.REGISTRATION_PAGE_PATH);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrateUser(@ModelAttribute(Parameters.NEW_USER) @Valid UserDTO userDTO,
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
                if (userService.checkIsNewUser(user.getLogin(), account.getAccountNumber())) {
                    userService.bookUser(user, account);
                    model.addAttribute(Parameters.OPERATION_MESSAGE, messageSource.getMessage("message.successoperation", null, locale));
                    pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
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