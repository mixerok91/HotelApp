package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.RoomTypeService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import by.stepanov.hotel.service.impl.UploadedFileService;
import by.stepanov.hotel.service.impl.validator.RoomValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

public class CreateRoom implements Command {

    private static final Logger log = Logger.getLogger(CreateRoom.class);

    private static final String LOGIN_PAGE = "mainController?command=login_page";
    private static final String PERSONS_NUMBER = "personsNumber";
    private static final String COST_PER_DAY = "costPerDay";
    private static final String ROOM_NUMBER = "roomNumber";
    private static final String ROOM_TYPE = "roomType";
    private static final String MESSAGE = "message";
    private static final String ROOM_ADMINISTRATION_PAGE_CONTROLLER = "mainController?command=room_administration_page";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later";
    private static final String PARSE_NUMBER_ERROR = "Can't parse number.";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null) {
            log.info("Redirected to login page because session does not exist");
            response.sendRedirect(LOGIN_PAGE);
        }

        RoomService roomService = ServiceProvider.getRoomService();
        RoomTypeService roomTypeService = ServiceProvider.getRoomTypeService();

        Room room = new Room();

        try {
            Collection<Part> parts = request.getParts();
            String absolutePath = request.getServletContext().getRealPath("");

            room.setPersons(Integer.parseInt(request.getParameter(PERSONS_NUMBER)));
            room.setDayCost(Double.parseDouble(request.getParameter(COST_PER_DAY)));
            room.setRoomNumber(request.getParameter(ROOM_NUMBER));
            room.setRoomType(roomTypeService.readRoomType(request.getParameter(ROOM_TYPE)));

            if (RoomValidator.isRoomNameAppropriate(room)) {
                room.setPicturePath(UploadedFileService.saveUploadedFile(absolutePath, parts, room.getRoomNumber()));
                roomService.createRoom(room);
                log.info("Redirect to: " + ROOM_ADMINISTRATION_PAGE_CONTROLLER);
                response.sendRedirect(ROOM_ADMINISTRATION_PAGE_CONTROLLER);
            } else {
                request.setAttribute(MESSAGE, "Room '" + room.getRoomNumber() + "' already exist");
                log.info("Dispatched to " + ROOM_ADMINISTRATION_PAGE_CONTROLLER + " with error");
                request.getRequestDispatcher(ROOM_ADMINISTRATION_PAGE_CONTROLLER).forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute(MESSAGE, PARSE_NUMBER_ERROR);
            log.info("Dispatched to " + ROOM_ADMINISTRATION_PAGE_CONTROLLER + " with error");
            request.getRequestDispatcher(ROOM_ADMINISTRATION_PAGE_CONTROLLER).forward(request, response);
        } catch (ServiceException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}