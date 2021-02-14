package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.service.ReservationService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class GetReservationsByPeriod implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ReservationService reservationService = ServiceProvider.getReservationService();

        String fromDate = request.getParameter("fromDate");
        String beforeDate = request.getParameter("beforeDate");
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("beforeDate", beforeDate);

        List<Reservation> reservationsByPeriod;

        try {
            if (LocalDate.parse(fromDate).isAfter(LocalDate.parse(beforeDate))){
                request.setAttribute("dateError","'Before' date must be after 'from' date");
                request.getRequestDispatcher("adminCabinet").forward(request, response);
            }

            reservationsByPeriod = reservationService.getReservationsByAfterDate(fromDate);
            reservationsByPeriod = reservationsByPeriod.stream()
                    .filter(reservation -> reservation.getInDate().isBefore(LocalDate.parse(beforeDate)))
                    .collect(Collectors.toList());
            request.setAttribute("reservationsByPeriod", reservationsByPeriod);

            request.getRequestDispatcher("adminCabinet").forward(request, response);
        } catch (ServiceException e) {
//            TODO logger
            System.err.println(e);
            response.sendRedirect("error?errorMessage=Ooops, something went wrong");
        }
    }
}
