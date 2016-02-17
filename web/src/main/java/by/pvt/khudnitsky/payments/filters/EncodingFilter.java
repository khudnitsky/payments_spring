/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class EncodingFilter implements Filter{

    @Override
    public void init(FilterConfig arg0) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //Set character encoding as UTF-8
        request.setCharacterEncoding("UTF-8");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
