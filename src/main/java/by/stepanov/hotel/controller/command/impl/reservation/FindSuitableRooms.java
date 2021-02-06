package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FindSuitableRooms implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String inDate = request.getParameter("inDate");
        String outDate = request.getParameter("outDate");
        String roomType = request.getParameter("roomType");
        boolean hasErrors = false;

        request.setAttribute("inDate", inDate);
        request.setAttribute("outDate", outDate);

        RoomService roomService = ServiceProvider.getRoomService();

        List<Room> freeRooms = null;

        try {
            if (LocalDate.parse(inDate).isAfter(LocalDate.parse(outDate))){
                request.setAttribute("dateError","Out date must be after in date");
                hasErrors = true;
            }
            if (LocalDate.parse(inDate).isBefore(LocalDate.now()) || LocalDate.parse(outDate).isBefore(LocalDate.now())){
                request.setAttribute("dateError", "Date must be not before today");
                hasErrors = true;
            }
            if (LocalDate.parse(inDate).isEqual(LocalDate.parse(outDate))){
                request.setAttribute("dataError", "In date and out date must not be equals");
                hasErrors = true;
            }

            if (hasErrors){
                request.getRequestDispatcher("reservationController?command=reservation_page").forward(request, response);
            }

            freeRooms = roomService.getFreeRooms(inDate, outDate, roomType);
            request.setAttribute("foundRooms", freeRooms);
            request.getRequestDispatcher("reservationController?command=reservation_page").forward(request, response);
        } catch (ServiceException e) {
            System.err.println(e);
            response.sendRedirect("error?errorMessage=Ooops, something went wrong, try later");
        }
    }
}
