package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetUsersReservationsByEmail implements Command {

    public static final String USER_NOT_FOUND = "User not found";
    public static final String MESSAGE = "message";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER = "user";
    public static final String USER_RESERVATIONS = "userReservations";
    public static final String ADMIN_CABINET = "adminCabinet";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        UserService userService = ServiceProvider.getInstance().getUserService();
        ReservationService reservationService = ServiceProvider.getReservationService();
        BillService billService = ServiceProvider.getBillService();

        String message = USER_NOT_FOUND;
        request.setAttribute(MESSAGE, message);

        String userEmail = request.getParameter(USER_EMAIL);

        try {
            User user = userService.readUser(userEmail);
            if (user != null) {
                request.setAttribute(USER, user);
                message = "User with email: '" + user.getEmail() + "' was found";
                request.setAttribute(MESSAGE, message);
                List<Reservation> userReservations = reservationService.getReservationsByUser(user);
                billService.setBillToReservations(userReservations);

                request.setAttribute(USER_RESERVATIONS, userReservations);
            }
        } catch (ServiceException e) {
            System.err.println(e);
//           TODO logger
        }

        request.getRequestDispatcher(ADMIN_CABINET).forward(request, response);
    }
}
