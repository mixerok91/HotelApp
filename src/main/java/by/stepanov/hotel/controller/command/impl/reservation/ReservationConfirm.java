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

    private static final String LOGIN_PAGE = "userController?command=login_page";
    public static final String SELECTED_RESERVATION = "selectedReservation";
    public static final String SELECTED_BILL = "selectedBill";
    public static final String MAIN_PAGE_CONTROLLER = "mainController?command=main_page";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong.";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            response.sendRedirect(LOGIN_PAGE);
        }

        ReservationService reservationService = ServiceProvider.getReservationService();
        BillService billService = ServiceProvider.getBillService();

        try {
            Reservation reservation = (Reservation) request.getSession().getAttribute(SELECTED_RESERVATION);
            Bill bill = (Bill) request.getSession().getAttribute(SELECTED_BILL);
            reservation.setCreationTime(LocalDateTime.now());
            reservation.setVisible(true);
            bill.setReservation(reservation);

            reservationService.createReservation(reservation);
            billService.createBill(bill);

            request.getSession().removeAttribute(SELECTED_RESERVATION);
            request.getSession().removeAttribute(SELECTED_BILL);

            response.sendRedirect(MAIN_PAGE_CONTROLLER);
        } catch (ServiceException|ClassCastException e) {
            System.err.println(e);
            response.sendRedirect(ERROR_PAGE);
        }
    }
}
