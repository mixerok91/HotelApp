package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Bill;
import by.stepanov.hotel.entity.BookStatus;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReservationConfirmPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RoomService roomService = ServiceProvider.getRoomService();

        try {
            Reservation reservation = new Reservation();
            reservation.setRoom(roomService.readRoom(Long.parseLong(request.getParameter("roomId"))));
            reservation.setUser((User) request.getSession().getAttribute("user"));
            reservation.setInDate(LocalDate.parse(request.getParameter("inDate")));
            reservation.setOutDate(LocalDate.parse(request.getParameter("outDate")));
            reservation.setBookStatus(BookStatus.RESERVED);

            Bill bill = new Bill();
            bill.setPaid(false);
            long days = reservation.getInDate().until(reservation.getOutDate(), ChronoUnit.DAYS);
            bill.setTotalAmount(days * reservation.getRoom().getDayCost());

            bill.setReservation(reservation);

            request.getSession().setAttribute("selectedReservation", reservation);
            request.getSession().setAttribute("selectedBill", bill);

            response.sendRedirect("reservationConfirm");
        } catch (ServiceException e) {
            System.err.println(e);
            response.sendRedirect("error?errorMessage=Ooops, something went wrong");
        }
    }
}
