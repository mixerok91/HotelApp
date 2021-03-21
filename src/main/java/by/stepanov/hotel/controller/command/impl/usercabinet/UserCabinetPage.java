package by.stepanov.hotel.controller.command.impl.usercabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.ReservationService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserCabinetPage implements Command {

    private static final Logger log = Logger.getLogger(UserCabinetPage.class);

    private static final String LOGIN_PAGE = "mainController?command=login_page";
    private static final String USER = "user";
    private static final String USER_RESERVATIONS = "userReservations";
    private static final String USER_CABINET = "userCabinet";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong.";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            log.info("Redirected to login page because session does not exist");
            response.sendRedirect(LOGIN_PAGE);
        }

        ReservationService reservationService = ServiceProvider.getReservationService();
        HttpSession session = request.getSession();

        try {
            List<Reservation> actualUserReservations = reservationService.getActualUserReservations((User) session.getAttribute(USER));

            session.setAttribute(USER_RESERVATIONS, actualUserReservations);
            log.info("Set attribute " + USER_RESERVATIONS + " to session");

            log.info("Redirect to: " + USER_CABINET);
            response.sendRedirect(USER_CABINET);
        } catch (ServiceException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
        request.getSession().setAttribute("lastPath", USER_CABINET);
        log.info("Save last path to session: " + USER_CABINET);
    }
}
