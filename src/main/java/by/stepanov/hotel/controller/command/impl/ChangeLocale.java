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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String lang = request.getParameter(LANG);
        request.getSession().setAttribute(LOCALIZATION, lang);
        log.info("Set attribute " + LOCALIZATION + " to session");

        String lastPath = String.valueOf(request.getSession().getAttribute(LAST_PATH));
        if (lastPath != null){
            log.info("Redirect to " + lastPath);
            response.sendRedirect(lastPath);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
