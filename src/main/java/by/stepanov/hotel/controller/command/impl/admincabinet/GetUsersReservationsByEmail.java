package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetUsersReservationsByEmail implements Command {

    private static final Logger log = Logger.getLogger(GetUsersReservationsByEmail.class);

    private static final String USER_NOT_FOUND = "User not found";
    private static final String ADMIN_PAGE_MESSAGE = "adminPageMessage";
    private static final String USER_EMAIL = "userEmail";
    private static final String ADMIN_PAGE_USER = "adminPageUser";
    private static final String ADMIN_PAGE_USER_RESERVATIONS = "adminPageUserReservations";
    private static final String ADMIN_CABINET = "adminCabinet";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later";
    private static final String LOGIN_PAGE = "mainController?command=login_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            log.info("Redirected to login page because session does not exist");
            response.sendRedirect(LOGIN_PAGE);
        }

        UserService userService = ServiceProvider.getUserService();
        ReservationService reservationService = ServiceProvider.getReservationService();
        BillService billService = ServiceProvider.getBillService();

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

            log.info("Dispatched to " + ADMIN_CABINET + " with reservation list by email");
            request.getRequestDispatcher(ADMIN_CABINET).forward(request, response);
        } catch (ServiceException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
