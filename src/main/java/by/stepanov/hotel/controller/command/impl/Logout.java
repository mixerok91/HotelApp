package by.stepanov.hotel.controller.command.impl;

import by.stepanov.hotel.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Logout implements Command {

    public static final String USER = "user";
    public static final String MAIN_PAGE_CONTROLLER = "mainController?command=main_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getSession().getAttribute(USER) != null) {
            request.getSession().removeAttribute(USER);
        }
        response.sendRedirect(MAIN_PAGE_CONTROLLER);
    }
}
