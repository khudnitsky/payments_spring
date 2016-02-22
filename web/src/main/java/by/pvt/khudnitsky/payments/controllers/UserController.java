package by.pvt.khudnitsky.payments.controllers;

import by.pvt.khudnitsky.payments.services.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * Created by: khudnitsky
 * Date: 22.02.2016
 * Time: 11:39
 */

@Controller
public class UserController {
    private IUserService userService;

    @Inject
    public UserController(IUserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String showHomePage(){
        return "index";
    }
}
