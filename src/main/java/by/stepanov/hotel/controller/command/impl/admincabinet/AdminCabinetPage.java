package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminCabinetPage implements Command {

    private static final Logger log = Logger.getLogger(AdminCabinetPage.class);

    private static final String ADMIN_CABINET = "adminCabinet";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Redirect to: " + ADMIN_CABINET);
        response.sendRedirect(ADMIN_CABINET);
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", ADMIN_CABINET);
        log.info("Save last path to session: " + ADMIN_CABINET);
    }
}
