package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RoomAdministrationPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            List<Room> roomList = ServiceProvider.getRoomService().getAllRooms();

            request.getSession().setAttribute("rooms", roomList);

            if (request.getAttribute("message") != null){
                String message = (String) request.getAttribute("message");
                response.sendRedirect("roomAdministration?message=" + message);
            } else {
                response.sendRedirect("roomAdministration");
            }
        } catch (ServiceException e) {
            System.err.println(e);
//            TODO logger
            response.sendRedirect("error?errorMessage=Ooops, something went wrong, try later");
        }
    }
}
