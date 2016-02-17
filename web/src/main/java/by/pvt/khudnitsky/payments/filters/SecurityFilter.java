/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.filters;

import by.pvt.khudnitsky.payments.commands.factory.CommandType;
import by.pvt.khudnitsky.payments.enums.PagePath;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.managers.ConfigurationManager;
import by.pvt.khudnitsky.payments.utils.RequestParameterParser;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityFilter implements Filter {
    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        AccessLevelType type = RequestParameterParser.getUserType(httpRequest);
        try {
            CommandType commandType = RequestParameterParser.getCommandType(httpRequest);
            if (type == null) {
                if (commandType == CommandType.LOGIN) {
                    chain.doFilter(request, response);
                } else if (commandType == CommandType.GOTOREGISTRATION) {
                    chain.doFilter(request, response);
                } else if (commandType == CommandType.REGISTRATION) {
                    chain.doFilter(request, response);
                } else {
                    String page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
                    RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                    dispatcher.forward(httpRequest, httpResponse);
                    session.invalidate();
                }
            } else {
                chain.doFilter(request, response);
            }
        }
        catch(IllegalArgumentException e) {
            String page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(httpRequest, httpResponse);
        }
    }

    public void destroy() {}
}
