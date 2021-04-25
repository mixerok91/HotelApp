package by.stepanov.hotel.controller.command.impl;

import by.stepanov.hotel.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLocale implements Command {

    private static final Logger log = Logger.getLogger(ChangeLocale.class);

    private static final String LOCALIZATION = "localization";
    private static final String LANG = "lang";
    private static final String LAST_PATH = "lastPath";
    private static final String MAIN_PAGE = "mainController?command=main_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String lang = request.getParameter(LANG);
        request.getSession().setAttribute(LOCALIZATION, lang);
        log.info("Set attribute " + LOCALIZATION + " to session");

        if (request.getSession().getAttribute(LAST_PATH) != null) {
            String lastPath = String.valueOf(request.getSession().getAttribute(LAST_PATH));
            log.info("Redirect to " + lastPath);
            response.sendRedirect(lastPath);
        }

        if (request.getSession().getAttribute(LAST_PATH) == null) {
            String lastPath = String.valueOf(request.getSession().getAttribute(LAST_PATH));
            log.info("Redirect to " + MAIN_PAGE);
            response.sendRedirect(MAIN_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
