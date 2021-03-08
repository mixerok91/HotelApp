package by.stepanov.hotel.controller.command.impl.usercabinet;

import by.stepanov.hotel.controller.command.Command;
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
import java.util.List;

public class ShowAllUsersReservations implements Command {

    private static final String LOGIN_PAGE = "mainController?command=login_page";
    public static final String USER = "user";
    public static final String USER_RESERVATIONS = "userReservations";
    public static final String USER_CABINET = "userCabinet";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong.";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            response.sendRedirect(LOGIN_PAGE);
        }

        ReservationService reservationService = ServiceProvider.getReservationService();
        HttpSession session = request.getSession();

        try {
            List<Reservation> userReservations =
                    reservationService.getReservationsByUser((User) session.getAttribute(USER));

            session.setAttribute(USER_RESERVATIONS, userReservations);

            response.sendRedirect(USER_CABINET);
        } catch (ServiceException e) {
            System.err.println(e);
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", USER_CABINET);
    }
}
