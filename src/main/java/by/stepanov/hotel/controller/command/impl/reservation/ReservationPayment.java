package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Bill;
import by.stepanov.hotel.entity.BookStatus;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.service.BillService;
import by.stepanov.hotel.service.ReservationService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReservationPayment implements Command {

    private static final Logger log = Logger.getLogger(ReservationPayment.class);

    private static final String LOGIN_PAGE = "mainController?command=login_page";
    private static final String RESERVATION_ID = "reservationId";
    private static final String USER_CABINET_PAGE_CONTROLLER = "mainController?command=user_cabinet_page";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            log.info("Redirected to login page because session does not exist");
            response.sendRedirect(LOGIN_PAGE);
        }

        ReservationService reservationService = ServiceProvider.getReservationService();
        BillService billService = ServiceProvider.getBillService();

        try {
            Reservation reservation =
                    reservationService.readReservation(Long.parseLong(request.getParameter(RESERVATION_ID)));

            Bill bill = billService.readBillByReservationId(reservation.getId());
            bill.setPaid(true);
            reservation.setBookStatus(BookStatus.PAID_FOR);

            reservationService.updateReservation(reservation);
            billService.updateBill(bill);

            log.info("Redirect to: " + USER_CABINET_PAGE_CONTROLLER);
            response.sendRedirect(USER_CABINET_PAGE_CONTROLLER);
        } catch (ServiceException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
