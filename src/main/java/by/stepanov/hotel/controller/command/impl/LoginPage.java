package by.stepanov.hotel.controller.command.impl;

import by.stepanov.hotel.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginPage implements Command {

    private static final Logger log = Logger.getLogger(LoginPage.class);

    private static final String LOGIN = "login";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Redirect to: " + LOGIN);
        response.sendRedirect(LOGIN);
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", LOGIN);
        log.info("Save last path to session: " + LOGIN);
    }
}
