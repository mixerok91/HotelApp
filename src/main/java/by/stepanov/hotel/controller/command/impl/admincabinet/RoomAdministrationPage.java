package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RoomAdministrationPage implements Command {

    private static final Logger log = Logger.getLogger(RoomAdministrationPage.class);

    private static final String LOGIN_PAGE = "mainController?command=login_page";
    private static final String ROOMS = "rooms";
    private static final String MESSAGE = "message";
    private static final String ROOM_ADMINISTRATION = "roomAdministration";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession()==null){
            log.info("Redirected to login page because session does not exist");
            response.sendRedirect(LOGIN_PAGE);
        }

        try {
            List<Room> roomList = ServiceProvider.getRoomService().getAllRooms();
            request.getSession().setAttribute(ROOMS, roomList);

            if (request.getAttribute(MESSAGE) != null){
                String message = (String) request.getAttribute(MESSAGE);
                log.info("Redirect to: room administration page with message");
                response.sendRedirect("roomAdministration?message=" + message);
            } else {
                log.info("Redirected to: " + ROOM_ADMINISTRATION);
                response.sendRedirect(ROOM_ADMINISTRATION);
            }
        } catch (ServiceException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", ROOM_ADMINISTRATION);
        log.info("Save last path to session: " + ROOM_ADMINISTRATION);
    }
}
