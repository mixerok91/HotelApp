package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Reservation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReservationUndo implements Command {

    private static final String LOGIN_PAGE = "userController?command=login_page";
    public static final String SELECTED_RESERVATION = "selectedReservation";
    public static final String IN_DATE = "inDate";
    public static final String OUT_DATE = "outDate";
    public static final String SELECTED_BILL = "selectedBill";
    public static final String RESERVATION_PAGE_CONTROLLER = "reservationController?command=reservation_page";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            response.sendRedirect(LOGIN_PAGE);
        }

        try {
            Reservation reservation = (Reservation) request.getSession().getAttribute(SELECTED_RESERVATION);

            request.setAttribute(IN_DATE, reservation.getInDate());
            request.setAttribute(OUT_DATE, reservation.getOutDate());

            request.getSession().removeAttribute(SELECTED_BILL);

            request.getRequestDispatcher(RESERVATION_PAGE_CONTROLLER).forward(request, response);
        } catch (ClassCastException e){
            System.err.println(e);
            response.sendRedirect(ERROR_PAGE);
        }
    }
}
