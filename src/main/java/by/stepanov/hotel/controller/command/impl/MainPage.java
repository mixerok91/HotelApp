package by.stepanov.hotel.controller.command.impl;

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

public class MainPage implements Command {

    public static final String ROOM_TYPES = "roomTypes";
    public static final String MAIN = "main";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later.";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RoomTypeService roomTypeService = ServiceProvider.getRoomTypeService();

        try {
            List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
            request.getSession().setAttribute(ROOM_TYPES, roomTypes);
            response.sendRedirect(MAIN);
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE);
        }
    }
}
