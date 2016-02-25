package by.pvt.khudnitsky.payments.utils;

import by.pvt.khudnitsky.payments.commands.ICommand;
import by.pvt.khudnitsky.payments.commands.factory.CommandFactory;
import by.pvt.khudnitsky.payments.enums.PagePath;
import by.pvt.khudnitsky.payments.managers.PagePathManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class RequestHandler {
    private RequestHandler() {
    }

    public static void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandFactory commandFactory = CommandFactory.getInstance();
        ICommand сommand = commandFactory.defineCommand(request);
        String page = сommand.execute(request);
        if (page != null) {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            page = PagePathManager.getInstance().getProperty(PagePath.HOME_PAGE_PATH);
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
