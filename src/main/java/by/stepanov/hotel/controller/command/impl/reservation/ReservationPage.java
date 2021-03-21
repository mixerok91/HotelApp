package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.RoomType;
import by.stepanov.hotel.service.RoomTypeService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReservationPage implements Command {

    private static final Logger log = Logger.getLogger(ReservationPage.class);

    private static final String ROOM_TYPES = "roomTypes";
    private static final String RESERVATION = "reservation";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RoomTypeService roomTypeService = ServiceProvider.getRoomTypeService();

        try {
            List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
            request.getSession().setAttribute(ROOM_TYPES, roomTypes);
            log.info("Redirect to: " + RESERVATION);
            response.sendRedirect(RESERVATION);
        } catch (ServiceException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", RESERVATION);
        log.info("Save last path to session: " + RESERVATION);
    }
}
