package by.stepanov.hotel.controller.command.impl;

import by.stepanov.hotel.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationPage implements Command {

    private static final Logger log = Logger.getLogger(RegistrationPage.class);

    private static final String REGISTRATION = "registration";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Redirect to: " + REGISTRATION);
        response.sendRedirect(REGISTRATION);
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", REGISTRATION);
        log.info("Save last path to session: " + REGISTRATION);
    }
}
