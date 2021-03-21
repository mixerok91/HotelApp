package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Bill;
import by.stepanov.hotel.service.BillService;
import by.stepanov.hotel.service.ReservationService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class ReservationConfirm implements Command {

    private static final Logger log = Logger.getLogger(ReservationConfirm.class);

    private static final String LOGIN_PAGE = "mainController?command=login_page";
    private static final String SELECTED_BILL = "selectedBill";
    private static final String MAIN_PAGE_CONTROLLER = "mainController?command=main_page";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong.";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            log.info("Redirected to login page because session does not exist");
            response.sendRedirect(LOGIN_PAGE);
        }

        ReservationService reservationService = ServiceProvider.getReservationService();
        BillService billService = ServiceProvider.getBillService();

        try {
            Bill bill = (Bill) request.getSession().getAttribute(SELECTED_BILL);
            bill.getReservation().setCreationTime(LocalDateTime.now());
            bill.getReservation().setVisible(true);

            reservationService.createReservation(bill.getReservation());
            billService.createBill(bill);

            request.getSession().removeAttribute(SELECTED_BILL);
            log.info("Attribute " + SELECTED_BILL + " deleted from session");

            log.info("Redirect to error page");
            response.sendRedirect(MAIN_PAGE_CONTROLLER);
        } catch (ServiceException|ClassCastException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
