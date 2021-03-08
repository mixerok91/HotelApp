package by.stepanov.hotel.controller.command.impl;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Logout implements Command {

    public static final String USER = "user";
    public static final String MAIN_PAGE_CONTROLLER = "mainController?command=main_page";

    static final Logger log = Logger.getLogger(Logout.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getSession().getAttribute(USER) != null) {
            User user =(User) request.getSession().getAttribute(USER);
            log.info("User with email: '" + user.getEmail()  + "' logged out");
            request.getSession().removeAttribute(USER);
        }
        response.sendRedirect(MAIN_PAGE_CONTROLLER);
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", MAIN_PAGE_CONTROLLER);
    }
}
