package by.stepanov.hotel.controller.command.impl.usercabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.BookStatus;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.ReservationService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class UserCabinetPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ReservationService reservationService = ServiceProvider.getReservationService();
        HttpSession session = request.getSession();

        try {
            List<Reservation> userReservations =
                    reservationService.getReservationsByUser((User) session.getAttribute("user"));

            userReservations.removeIf(reservation -> reservation.getBookStatus().equals(BookStatus.CANCELLED)
                    ||reservation.getBookStatus().equals(BookStatus.FINISHED)
                    ||reservation.getInDate().isBefore(LocalDate.now()));

            session.setAttribute("userReservations", userReservations);

            response.sendRedirect("userCabinet");
        } catch (ServiceException e) {
            System.err.println(e);
            response.sendRedirect("error?errorMessage=Ooops, something went wrong.");
        }
    }
}
