package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.service.ReservationService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class ReservationConfirm implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ReservationService reservationService = ServiceProvider.getReservationService();
        try {
            Reservation reservation = (Reservation) request.getSession().getAttribute("selectedReservation");
            reservation.setCreationTime(LocalDateTime.now());
            reservation.setVisible(true);
            reservationService.createReservation(reservation);
            request.getSession().removeAttribute("selectedReservation");

            response.sendRedirect("mainController?command=main_page");
        } catch (ServiceException e) {
            System.err.println(e);
            response.sendRedirect("error?errorMessage=Ooops, something went wrong.");
        }
    }
}
