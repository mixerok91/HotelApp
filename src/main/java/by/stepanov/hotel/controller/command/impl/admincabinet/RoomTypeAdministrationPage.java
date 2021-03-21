package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.RoomType;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RoomTypeAdministrationPage implements Command {

    private static final Logger log = Logger.getLogger(RoomTypeAdministrationPage.class);

    private static final String LOGIN_PAGE = "mainController?command=login_page";
    private static final String ROOM_TYPES = "roomTypes";
    private static final String MESSAGE = "message";
    private static final String ROOM_TYPE_ADMINISTRATION = "roomTypeAdministration";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            log.info("Redirected to login page because session does not exist");
            response.sendRedirect(LOGIN_PAGE);
        }

        try {
            List<RoomType> roomTypeList = ServiceProvider.getRoomTypeService().getAllRoomTypes();
            request.getSession().setAttribute(ROOM_TYPES, roomTypeList);

            if (request.getAttribute(MESSAGE) != null){
                String message = (String) request.getAttribute(MESSAGE);
                log.info("Redirect to: room type administration with message");
                response.sendRedirect("roomTypeAdministration?message=" + message);
            } else {
                log.info("Redirect to: " + ROOM_TYPE_ADMINISTRATION);
                response.sendRedirect(ROOM_TYPE_ADMINISTRATION);
            }
        } catch (ServiceException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", ROOM_TYPE_ADMINISTRATION);
        log.info("Save last path to session: " + ROOM_TYPE_ADMINISTRATION);
    }
}
