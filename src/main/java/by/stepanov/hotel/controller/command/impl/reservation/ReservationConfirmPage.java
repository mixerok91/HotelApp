package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Bill;
import by.stepanov.hotel.entity.BookStatus;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class ReservationConfirmPage implements Command {

    private static final String LOGIN_PAGE = "userController?command=login_page";
    public static final String ROOM_ID = "roomId";
    public static final String USER = "user";
    public static final String IN_DATE = "inDate";
    public static final String OUT_DATE = "outDate";
    public static final String SELECTED_BILL = "selectedBill";
    public static final String RESERVATION_CONFIRM = "reservationConfirm";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            response.sendRedirect(LOGIN_PAGE);
        }

        RoomService roomService = ServiceProvider.getRoomService();
        Reservation reservation = new Reservation();

        try {
            reservation.setRoom(roomService.readRoom(Long.parseLong(request.getParameter(ROOM_ID))));
            reservation.setUser((User) request.getSession().getAttribute(USER));
            reservation.setInDate(LocalDate.parse(request.getParameter(IN_DATE)));
            reservation.setOutDate(LocalDate.parse(request.getParameter(OUT_DATE)));
            reservation.setBookStatus(BookStatus.RESERVED);

            Bill bill = new Bill(reservation);
            bill.setPaid(false);

            request.getSession().setAttribute(SELECTED_BILL, bill);

            response.sendRedirect(RESERVATION_CONFIRM);
        } catch (ServiceException e) {
            System.err.println(e);
            response.sendRedirect(ERROR_PAGE);
        }
    }
}
