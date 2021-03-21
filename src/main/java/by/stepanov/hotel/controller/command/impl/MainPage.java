package by.stepanov.hotel.controller.command.impl;

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

public class MainPage implements Command {

    private static final Logger log = Logger.getLogger(MainPage.class);

    private static final String ROOM_TYPES = "roomTypes";
    private static final String MAIN = "main";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later.";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RoomTypeService roomTypeService = ServiceProvider.getRoomTypeService();

        if (request.getSession(true).getAttribute("localization") == null){
            request.getSession().setAttribute("localization", "eng");
        }

        try {
            List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
            request.getSession().setAttribute(ROOM_TYPES, roomTypes);
            log.info("Redirect to: " + ROOM_TYPES);
            response.sendRedirect(MAIN);
        } catch (ServiceException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", MAIN);
        log.info("Save last path to session: " + MAIN);
    }
}
