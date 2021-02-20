package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.RoomType;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RoomTypeAdministrationPage implements Command {

    public static final String LOGIN_PAGE = "userController?command=login_page";
    public static final String ROOM_TYPES = "roomTypes";
    public static final String MESSAGE = "message";
    public static final String ROOM_TYPE_ADMINISTRATION = "roomTypeAdministration";
    public static final String ERROR = "error?errorMessage=Ooops, something went wrong, try later";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            if (request.getSession() == null){
                response.sendRedirect(LOGIN_PAGE);
            }

            List<RoomType> roomTypeList = ServiceProvider.getRoomTypeService().getAllRoomTypes();
            request.getSession().setAttribute(ROOM_TYPES, roomTypeList);

            if (request.getAttribute(MESSAGE) != null){
                String message = (String) request.getAttribute(MESSAGE);
                response.sendRedirect("roomTypeAdministration?message=" + message);
            } else {
                response.sendRedirect(ROOM_TYPE_ADMINISTRATION);
            }
        } catch (ServiceException e) {
            System.err.println(e);
//            TODO logger
            response.sendRedirect(ERROR);
        }
    }
}
