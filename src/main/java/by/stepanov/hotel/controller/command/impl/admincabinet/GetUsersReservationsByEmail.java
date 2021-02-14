package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Bill;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Provider;
import java.util.List;

public class GetUsersReservationsByEmail implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        UserService userService = ServiceProvider.getInstance().getUserService();
        ReservationService reservationService = ServiceProvider.getInstance().getReservationService();
        BillService billService = ServiceProvider.getInstance().getBillService();

        String message = "User not found";
        request.setAttribute("message", message);

        String userEmail = request.getParameter("userEmail");

        try {
            User user = userService.readUser(userEmail);
            if (user != null){
                request.setAttribute("user", user);
                message = "User with email: '" + user.getEmail() + "' was found";
                request.setAttribute("message", message);
                List<Reservation> userReservations = reservationService.getReservationsByUser(user);
                if (!userReservations.isEmpty()) {
                    userReservations
                            .forEach(reservation -> {
                                try {
                                    reservation.setBill(billService.readBillByReservationId(reservation.getId()));
                                } catch (ServiceException e) {
                                    System.err.println(e);
//                        TODO logger
                                }
                            });
                    request.setAttribute("userReservations", userReservations);
                }
            }
        } catch (ServiceException e) {
           System.err.println(e);
//           TODO logger
        }

        request.getRequestDispatcher("adminCabinet").forward(request, response);
    }
}
