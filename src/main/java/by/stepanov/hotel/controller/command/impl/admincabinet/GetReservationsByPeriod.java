package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class GetReservationsByPeriod implements Command {

    private static final Logger log = Logger.getLogger(GetReservationsByPeriod.class);

    private static final String FROM_DATE = "fromDate";
    private static final String BEFORE_DATE = "beforeDate";
    private static final String DATE_ERROR = "dateError";
    private static final String BEFORE_DATE_MUST_BE_AFTER_FROM_DATE = "'Before' date must be after 'from' date";
    private static final String ADMIN_CABINET = "adminCabinet";
    private static final String RESERVATIONS_BY_PERIOD = "reservationsByPeriod";
    private static final String NO_RESERVATIONS_IN_THIS_PERIOD = "In this period reservations didn't find";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong";
    private static final String LOGIN_PAGE = "mainController?command=login_page";
    private static final String PARSE_DATA_ERROR = "Can't parse data";
    private static final String ADMIN_PAGE_MESSAGE = "adminPageMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ReservationService reservationService = ServiceProvider.getReservationService();
        BillService billService = ServiceProvider.getBillService();

        if (request.getSession() == null){
            log.info("Redirected to login page because session does not exist");
            response.sendRedirect(LOGIN_PAGE);
        }

        String fromDate = request.getParameter(FROM_DATE);
        String beforeDate = request.getParameter(BEFORE_DATE);
        request.setAttribute(FROM_DATE, fromDate);
        request.setAttribute(BEFORE_DATE, beforeDate);

        List<Reservation> reservationsByPeriod;

        try {
            if (LocalDate.parse(fromDate).isAfter(LocalDate.parse(beforeDate))){
                request.setAttribute(DATE_ERROR,BEFORE_DATE_MUST_BE_AFTER_FROM_DATE);
                log.info("Dispatched to " + ADMIN_CABINET + " with error");
                request.getRequestDispatcher(ADMIN_CABINET).forward(request, response);
            } else {
                reservationsByPeriod = reservationService.getReservationByPeriod(fromDate, beforeDate);
                billService.setBillToReservations(reservationsByPeriod);
                request.setAttribute(RESERVATIONS_BY_PERIOD, reservationsByPeriod);

                if (reservationsByPeriod.isEmpty()){
                    request.setAttribute(ADMIN_PAGE_MESSAGE, NO_RESERVATIONS_IN_THIS_PERIOD);
                }

                log.info("Dispatched to " + ADMIN_CABINET + " with reservation list");
                request.getRequestDispatcher(ADMIN_CABINET).forward(request, response);
            }
        } catch (DateTimeParseException e) {
            request.setAttribute(DATE_ERROR, PARSE_DATA_ERROR);
            log.info("Dispatched to " + ADMIN_CABINET + " with error");
            request.getRequestDispatcher(ADMIN_CABINET).forward(request, response);
        }catch (ServiceException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
