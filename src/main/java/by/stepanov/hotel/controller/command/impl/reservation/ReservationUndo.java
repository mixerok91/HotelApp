package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Bill;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReservationUndo implements Command {

    private static final Logger log = Logger.getLogger(ReservationUndo.class);

    private static final String LOGIN_PAGE = "mainController?command=login_page";
    private static final String IN_DATE = "inDate";
    private static final String OUT_DATE = "outDate";
    private static final String SELECTED_BILL = "selectedBill";
    private static final String RESERVATION_PAGE = "/reservation";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            log.info("Redirected to login page because session does not exist");
            response.sendRedirect(LOGIN_PAGE);
        }

        try {
            Bill bill = (Bill) request.getSession().getAttribute(SELECTED_BILL);

            request.setAttribute(IN_DATE, bill.getReservation().getInDate());
            request.setAttribute(OUT_DATE, bill.getReservation().getOutDate());

            request.getSession().removeAttribute(SELECTED_BILL);
            log.info("Attribute " + SELECTED_BILL + " removed from session");

            log.info("Dispatched to " + RESERVATION_PAGE);
            request.getRequestDispatcher(RESERVATION_PAGE).forward(request, response);
        } catch (ClassCastException e){
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
