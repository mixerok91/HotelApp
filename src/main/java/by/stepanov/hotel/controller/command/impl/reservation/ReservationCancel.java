package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.BookStatus;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.service.ReservationService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReservationCancel implements Command {

    private static final String LOGIN_PAGE = "mainController?command=login_page";
    private static final String RESERVATION_ID = "reservationId";
    private static final String USER_CABINET_PAGE_CONTROLLER = "mainController?command=user_cabinet_page";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            response.sendRedirect(LOGIN_PAGE);
        }

        ReservationService reservationService = ServiceProvider.getReservationService();

        try {
            Reservation reservation =
                    reservationService.readReservation(Long.parseLong(request.getParameter(RESERVATION_ID)));
            reservation.setBookStatus(BookStatus.CANCELLED);

            reservationService.updateReservation(reservation);

            response.sendRedirect(USER_CABINET_PAGE_CONTROLLER);
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}

