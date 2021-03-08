package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RoomAdministrationPage implements Command {

    public static final String LOGIN_PAGE = "mainController?command=login_page";
    public static final String ROOMS = "rooms";
    public static final String MESSAGE = "message";
    public static final String ROOM_ADMINISTRATION = "roomAdministration";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession()==null){
            response.sendRedirect(LOGIN_PAGE);
        }

        try {
            List<Room> roomList = ServiceProvider.getRoomService().getAllRooms();
            request.getSession().setAttribute(ROOMS, roomList);

            if (request.getAttribute(MESSAGE) != null){
                String message = (String) request.getAttribute(MESSAGE);
                response.sendRedirect("roomAdministration?message=" + message);
            } else {
                response.sendRedirect(ROOM_ADMINISTRATION);
            }
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", ROOM_ADMINISTRATION);
    }
}
