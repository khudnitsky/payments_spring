package by.pvt.khudnitsky.payments.authentication;

import by.pvt.khudnitsky.payments.entities.AccessLevel;
import by.pvt.khudnitsky.payments.entities.User;
import by.pvt.khudnitsky.payments.exceptions.ServiceException;
import by.pvt.khudnitsky.payments.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: khudnitsky
 * Date: 29.02.2016
 * Time: 15:18
 */

@Service("authenticationService")
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    @Transactional(readOnly = true)
    public CustomUser loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userService.getUserByLogin(login);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
        }
        catch (ServiceException e) {
            e.printStackTrace();
        }
        return new CustomUser(user, true, true, true, true, getGrantedAuthorities(user));
    }




    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (AccessLevel access : user.getAccessLevels()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + access.getAccessLevelType().toString()));
        }
        return authorities;
    }
}