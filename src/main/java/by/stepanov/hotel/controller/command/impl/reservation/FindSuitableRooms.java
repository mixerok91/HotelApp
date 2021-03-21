package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import by.stepanov.hotel.service.impl.validator.SuitableDateValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FindSuitableRooms implements Command {

    private static final Logger log = Logger.getLogger(FindSuitableRooms.class);

    private static final String IN_DATE = "inDate";
    private static final String OUT_DATE = "outDate";
    private static final String ROOM_TYPE = "roomType";
    private static final String DATE_ERROR = "dateError";
    private static final String RESERVATION_PAGE = "reservation";
    private static final String FOUND_ROOMS = "foundRooms";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later";

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
                log.info("Dispatched to " + RESERVATION_PAGE + " with error");
                request.getRequestDispatcher(RESERVATION_PAGE).forward(request, response);
            } else {
                List<Room> freeRooms = roomService.getFreeRooms(inDate, outDate, roomType);

                request.setAttribute(FOUND_ROOMS, freeRooms);
                log.info("Dispatched to " + RESERVATION_PAGE + " with free rooms");
                request.getRequestDispatcher(RESERVATION_PAGE).forward(request, response);
            }
        } catch (ServiceException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
