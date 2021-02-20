package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.RoomTypeService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import by.stepanov.hotel.service.impl.validator.RoomValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateRoom implements Command {

    private static final String LOGIN_PAGE = "userController?command=login_page";
    private static final String PERSONS_NUMBER = "personsNumber";
    private static final String COST_PER_DAY = "costPerDay";
    private static final String ROOM_NUMBER = "roomNumber";
    private static final String ROOM_TYPE = "roomType";
    private static final String MESSAGE = "message";
    private static final String ROOM_ADMINISTRATION_PAGE_CONTROLLER = "adminController?command=room_administration_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            response.sendRedirect(LOGIN_PAGE);
        }

        RoomService roomService = ServiceProvider.getRoomService();
        RoomTypeService roomTypeService = ServiceProvider.getRoomTypeService();

        Room room = new Room();

        try {
            room.setPersons(Integer.parseInt(request.getParameter(PERSONS_NUMBER)));
            room.setDayCost(Double.parseDouble(request.getParameter(COST_PER_DAY)));
            room.setRoomNumber(request.getParameter(ROOM_NUMBER));
            room.setRoomType(roomTypeService.readRoomType(request.getParameter(ROOM_TYPE)));

            if (RoomValidator.isRoomNameAppropriate(room)){
                roomService.saveUploadedFileAndAddPathToRoom(request, room);
                roomService.createRoom(room);

                request.setAttribute(MESSAGE, "Room '" + room.getRoomNumber() + "' created");
            } else {
                request.setAttribute(MESSAGE, "Room '" + room.getRoomNumber() + "' already exist");
            }
        } catch (ServiceException e) {
            request.setAttribute(MESSAGE, "Room '" + room.getRoomNumber() + "' did not create");
            System.err.println(e);
//            TODO logger
        }
        request.getRequestDispatcher(ROOM_ADMINISTRATION_PAGE_CONTROLLER).forward(request, response);
    }
}