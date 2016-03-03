package by.pvt.khudnitsky.payments.security;

import by.pvt.khudnitsky.payments.pojos.Account;
import by.pvt.khudnitsky.payments.pojos.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

/**
 * Wrapper-class on User
 * Added additional information about user
 * Created by: khudnitsky
 * Date: 02.03.2016
 * Time: 12:18
 */
public class CustomUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public CustomUser(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLogin(), user.getPassword(), authorities);
        this.user = user;
    }

    public CustomUser(User user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLogin(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    public String getUserFirstName() {
        return user.getFirstName();
    }

    public String getUserLastName() {
        return user.getLastName();
    }

    public Set<Account> getUserAccounts() {
        return user.getAccounts();
    }

    public String getUserLogin(){
        return user.getLogin();
    }
}
