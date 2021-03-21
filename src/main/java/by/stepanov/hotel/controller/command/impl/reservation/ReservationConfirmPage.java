package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Bill;
import by.stepanov.hotel.entity.BookStatus;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class ReservationConfirmPage implements Command {

    private static final Logger log = Logger.getLogger(ReservationConfirmPage.class);

    private static final String LOGIN_PAGE = "mainController?command=login_page";
    private static final String ROOM_ID = "roomId";
    private static final String USER = "user";
    private static final String IN_DATE = "inDate";
    private static final String OUT_DATE = "outDate";
    private static final String SELECTED_BILL = "selectedBill";
    private static final String RESERVATION_CONFIRM = "reservationConfirm";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            log.info("Redirected to login page because session does not exist");
            response.sendRedirect(LOGIN_PAGE);
        }

        RoomService roomService = ServiceProvider.getRoomService();
        Reservation reservation = new Reservation();

        try {
            reservation.setRoom(roomService.readRoom(Long.parseLong(request.getParameter(ROOM_ID))));
            reservation.setUser((User) request.getSession().getAttribute(USER));
            reservation.setInDate(LocalDate.parse(request.getParameter(IN_DATE)));
            reservation.setOutDate(LocalDate.parse(request.getParameter(OUT_DATE)));
            reservation.setBookStatus(BookStatus.RESERVED);

            Bill bill = new Bill(reservation);
            bill.setPaid(false);

            request.getSession().setAttribute(SELECTED_BILL, bill);

            log.info("Dispatched to " + RESERVATION_CONFIRM + " with selected bill");
            response.sendRedirect(RESERVATION_CONFIRM);
        } catch (ServiceException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", RESERVATION_CONFIRM);
        log.info("Save last path to session: " + RESERVATION_CONFIRM);
    }
}
