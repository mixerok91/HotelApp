package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Bill;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReservationUndo implements Command {

    private static final String LOGIN_PAGE = "mainController?command=login_page";
    public static final String IN_DATE = "inDate";
    public static final String OUT_DATE = "outDate";
    public static final String SELECTED_BILL = "selectedBill";
    public static final String RESERVATION_PAGE = "/reservation";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            response.sendRedirect(LOGIN_PAGE);
        }

        try {
            Bill bill = (Bill) request.getSession().getAttribute(SELECTED_BILL);

            request.setAttribute(IN_DATE, bill.getReservation().getInDate());
            request.setAttribute(OUT_DATE, bill.getReservation().getOutDate());

            request.getSession().removeAttribute(SELECTED_BILL);

            request.getRequestDispatcher(RESERVATION_PAGE).forward(request, response);
        } catch (ClassCastException e){
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
