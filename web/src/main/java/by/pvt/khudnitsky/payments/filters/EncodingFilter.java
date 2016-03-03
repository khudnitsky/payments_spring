/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.filters;

import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Encoding filter
 * Encodes into UTF-8
 * @author khudnitsky
 * @version 1.0
 *
 */

public class EncodingFilter implements Filter {
    private Logger logger = Logger.getLogger(EncodingFilter.class);
    private String initParamEncoding;

    @Override
    public void init(FilterConfig config) throws ServletException {
        initParamEncoding = config.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String requestEncoding = req.getCharacterEncoding();
        if (initParamEncoding != null && !initParamEncoding.equalsIgnoreCase(requestEncoding)) {
            req.setCharacterEncoding(initParamEncoding);
            resp.setCharacterEncoding(initParamEncoding);
        }
        resp.setContentType("UTF-8");
        logger.debug("CharacterEncoding was set");
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}
