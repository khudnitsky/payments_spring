package by.pvt.khudnitsky.payments.utils;

import by.pvt.khudnitsky.payments.constants.WebConstants;
import by.pvt.khudnitsky.payments.security.CustomUser;
import by.pvt.khudnitsky.payments.pojos.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Used foe authentication
 * Created by: khudnitsky
 * Date: 01.03.2016
 * Time: 14:47
 */

@Component
public class PrincipalUtil {
    public User getPrincipal(){
        User user = new User();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUser) {
            user.setFirstName(((CustomUser) principal).getUserFirstName());
            user.setLastName(((CustomUser) principal).getUserLastName());
            user.setAccounts(((CustomUser) principal).getUserAccounts());
            user.setLogin(((CustomUser) principal).getUserLogin());
        } else {
            user = new User();
            user.setFirstName(WebConstants.ANONYMOUS);
        }
        return user;
    }
}