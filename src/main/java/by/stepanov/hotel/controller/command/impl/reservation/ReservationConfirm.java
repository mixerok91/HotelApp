package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Bill;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.service.BillService;
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
        BillService billService = ServiceProvider.getBillService();

        try {
            Reservation reservation = (Reservation) request.getSession().getAttribute("selectedReservation");
            Bill bill = (Bill) request.getSession().getAttribute("selectedBill");
            reservation.setCreationTime(LocalDateTime.now());
            reservation.setVisible(true);
            bill.setReservation(reservation);

            reservationService.createReservation(reservation);
            billService.createBill(bill);

            request.getSession().removeAttribute("selectedReservation");
            request.getSession().removeAttribute("selectedBill");

            response.sendRedirect("mainController?command=main_page");
        } catch (ServiceException|ClassCastException e) {
            System.err.println(e);
            response.sendRedirect("error?errorMessage=Ooops, something went wrong.");
        }
    }
}
