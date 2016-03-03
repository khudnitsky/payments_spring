package by.pvt.khudnitsky.payments.security;

import by.pvt.khudnitsky.payments.constants.WebConstants;
import by.pvt.khudnitsky.payments.enums.PagePath;
import by.pvt.khudnitsky.payments.enums.Parameters;
import by.pvt.khudnitsky.payments.managers.PagePathManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * Success Authentication Handler
 * Created by: khudnitsky
 * Date: 01.03.2016
 * Time: 13:19
 */

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private Logger logger = Logger.getLogger(this.getClass());
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private PagePathManager pagePathManager;

    @Autowired
    private MessageSource messageSource;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.info(WebConstants.UNABLE_TO_REDIRECT + targetUrl);
            return;
        }
        request.getSession().setAttribute(Parameters.WRONG_LOGIN_OR_PASSWORD, messageSource.getMessage("message.loginerror", null, request.getLocale()));
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isClient = false;
        boolean isAdmin = false;
        String pagePath;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(WebConstants.ROLE_CLIENT)) {
                isClient = true;
                break;
            } else if (grantedAuthority.getAuthority().equals(WebConstants.ROLE_ADMINISTRATOR)) {
                isAdmin = true;
                break;
            }
        }
        if (isClient) {
            pagePath = "/" + pagePathManager.getProperty(PagePath.CLIENT_PAGE_PATH);
        } else if (isAdmin) {
            pagePath = "/" + pagePathManager.getProperty(PagePath.ADMIN_PAGE_PATH);
        } else {
//            throw new IllegalStateException();
            pagePath = /*"/" + */pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
        }
        return pagePath;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session;
        session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}