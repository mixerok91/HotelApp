package by.stepanov.hotel.controller.command.impl.usercabinet;

import by.stepanov.hotel.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserDataPage implements Command {

    private static final Logger log = Logger.getLogger(EditUserDataPage.class);

    private static final String EDIT_USER_DATA = "editUserData";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Redirect to: " + EDIT_USER_DATA);
        response.sendRedirect(EDIT_USER_DATA);
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", EDIT_USER_DATA);
        log.info("Save last path to session: " + EDIT_USER_DATA);
    }
}
