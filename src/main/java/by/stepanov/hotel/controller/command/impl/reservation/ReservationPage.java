package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.RoomType;
import by.stepanov.hotel.service.RoomTypeService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReservationPage implements Command {

    public static final String ROOM_TYPES = "roomTypes";
    public static final String RESERVATION = "/reservation";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RoomTypeService roomTypeService = ServiceProvider.getRoomTypeService();

        try {
            List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
            request.setAttribute(ROOM_TYPES, roomTypes);
            request.getRequestDispatcher(RESERVATION).forward(request, response);
        } catch (ServiceException e) {
//            TODO Logger
            System.err.println(e);
            response.sendRedirect(ERROR_PAGE);
        }
    }
}
