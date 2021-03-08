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
    public static final String ADMIN_PAGE_MESSAGE = "adminPageMessage";
    public static final String USER_EMAIL = "userEmail";
    public static final String ADMIN_PAGE_USER = "adminPageUser";
    public static final String ADMIN_PAGE_USER_RESERVATIONS = "adminPageUserReservations";
    public static final String ADMIN_CABINET = "adminCabinet";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later";
    private static final String LOGIN_PAGE = "mainController?command=login_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        UserService userService = ServiceProvider.getUserService();
        ReservationService reservationService = ServiceProvider.getReservationService();
        BillService billService = ServiceProvider.getBillService();

        if (request.getSession() == null){
            response.sendRedirect(LOGIN_PAGE);
        }

        String userEmail = request.getParameter(USER_EMAIL);

        try {
            User user = userService.readUser(userEmail);
            if (user != null) {
                request.setAttribute(ADMIN_PAGE_USER, user);
                request.setAttribute(ADMIN_PAGE_MESSAGE, "User with email: '" + user.getEmail() + "' was found");
                List<Reservation> userReservations = reservationService.getReservationsByUser(user);
                billService.setBillToReservations(userReservations);

                request.setAttribute(ADMIN_PAGE_USER_RESERVATIONS, userReservations);
            } else {
                request.setAttribute(ADMIN_PAGE_MESSAGE, USER_NOT_FOUND);
            }
            request.getRequestDispatcher(ADMIN_CABINET).forward(request, response);
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
