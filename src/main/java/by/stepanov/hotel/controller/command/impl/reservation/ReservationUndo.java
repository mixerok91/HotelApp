package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Reservation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReservationUndo implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Reservation reservation = (Reservation) request.getSession().getAttribute("selectedReservation");

            request.setAttribute("inDate", reservation.getInDate());
            request.setAttribute("outDate", reservation.getOutDate());

            request.getSession().removeAttribute("selectedReservation");
            request.getSession().removeAttribute("selectedBill");

            request.getRequestDispatcher("reservationController?command=reservation_page").forward(request, response);
        } catch (ClassCastException e){
            System.err.println(e);
            response.sendRedirect("error?errorMessage=Ooops, something went wrong, try later");
        }
    }
}
