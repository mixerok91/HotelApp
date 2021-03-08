package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.RoomTypeService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import by.stepanov.hotel.service.impl.UploadedFileService;
import by.stepanov.hotel.service.impl.validator.RoomValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

public class EditRoom implements Command {

    private static final String LOGIN_PAGE = "mainController?command=login_page";
    private static final String ROOM_ID = "roomId";
    private static final String PERSONS_NUMBER = "personsNumber";
    private static final String COST_PER_DAY = "costPerDay";
    private static final String ROOM_NUMBER = "roomNumber";
    public static final String PICTURE_PATH = "picturePath";
    public static final String ROOM_TYPE = "roomType";
    public static final String MESSAGE = "message";
    public static final String ROOM_ADMINISTRATION_PAGE_CONTROLLER = "mainController?command=room_administration_page";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later";
    public static final String PARSE_NUMBER_ERROR = "Can't parse number.";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null) {
            response.sendRedirect(LOGIN_PAGE);
        }

        RoomService roomService = ServiceProvider.getRoomService();
        RoomTypeService roomTypeService = ServiceProvider.getRoomTypeService();

        Room room = new Room();

        try {
            Collection<Part> parts = request.getParts();
            String absolutePath = request.getServletContext().getRealPath("");

            room.setId(Long.valueOf(request.getParameter(ROOM_ID)));
            room.setPersons(Integer.parseInt(request.getParameter(PERSONS_NUMBER)));
            room.setDayCost(Double.parseDouble(request.getParameter(COST_PER_DAY)));
            room.setRoomNumber(request.getParameter(ROOM_NUMBER));
            room.setPicturePath(request.getParameter(PICTURE_PATH));
            room.setRoomType(roomTypeService.readRoomType(request.getParameter(ROOM_TYPE)));

            if (RoomValidator.isRoomNameAppropriate(room)) {
                room.setPicturePath(UploadedFileService.saveUploadedFile(absolutePath, parts, room.getRoomNumber()));
                roomService.updateRoom(room);
                response.sendRedirect(ROOM_ADMINISTRATION_PAGE_CONTROLLER);
            } else {
                request.setAttribute(MESSAGE, "Room '" + room.getRoomNumber() + "' already exist");
                request.getRequestDispatcher(ROOM_ADMINISTRATION_PAGE_CONTROLLER).forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute(MESSAGE, PARSE_NUMBER_ERROR);
            request.getRequestDispatcher(ROOM_ADMINISTRATION_PAGE_CONTROLLER).forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
