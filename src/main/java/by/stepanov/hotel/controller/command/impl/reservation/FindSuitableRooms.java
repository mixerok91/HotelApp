package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import by.stepanov.hotel.service.impl.validator.SuitableDateValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FindSuitableRooms implements Command {

    public static final String IN_DATE = "inDate";
    public static final String OUT_DATE = "outDate";
    public static final String ROOM_TYPE = "roomType";
    public static final String DATE_ERROR = "dateError";
    public static final String RESERVATION_PAGE = "reservation";
    public static final String FOUND_ROOMS = "foundRooms";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RoomService roomService = ServiceProvider.getRoomService();

        String inDate = request.getParameter(IN_DATE);
        String outDate = request.getParameter(OUT_DATE);
        String roomType = request.getParameter(ROOM_TYPE);

        request.setAttribute(IN_DATE, inDate);
        request.setAttribute(OUT_DATE, outDate);

        try {
            if (SuitableDateValidator.checkDates(inDate, outDate) != null){
                request.setAttribute(DATE_ERROR, SuitableDateValidator.checkDates(inDate, outDate));
                request.getRequestDispatcher(RESERVATION_PAGE).forward(request, response);
            }

            List<Room> freeRooms = roomService.getFreeRooms(inDate, outDate, roomType);

            request.setAttribute(FOUND_ROOMS, freeRooms);
            request.getRequestDispatcher(RESERVATION_PAGE).forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
