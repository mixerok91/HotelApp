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

    public static final String RESERVATION_ID = "reservationId";
    public static final String USER_CABINET_PAGE_CONTROLLER = "reservationController?command=user_cabinet_page";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ReservationService reservationService = ServiceProvider.getReservationService();

        try {
            Reservation reservation =
                    reservationService.readReservation(Long.parseLong(request.getParameter(RESERVATION_ID)));
            reservation.setBookStatus(BookStatus.CANCELLED);

            reservationService.updateReservation(reservation);

            response.sendRedirect(USER_CABINET_PAGE_CONTROLLER);
        } catch (ServiceException e) {
            System.err.println(e);
            response.sendRedirect(ERROR_PAGE);
        }
    }
}

