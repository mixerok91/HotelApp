package by.stepanov.hotel.controller.command.impl.reservation;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Bill;
import by.stepanov.hotel.entity.BookStatus;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.service.BillService;
import by.stepanov.hotel.service.ReservationService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.temporal.ChronoUnit;

public class ReservationPayment implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ReservationService reservationService = ServiceProvider.getReservationService();
        BillService billService = ServiceProvider.getBillService();

        try {
            Reservation reservation =
                    reservationService.readReservation(Long.parseLong(request.getParameter("reservationId")));


            Bill bill = billService.readBillByReservationId(reservation.getId());
            bill.setPaid(true);
            reservation.setBookStatus(BookStatus.PAID_FOR);

            reservationService.updateReservation(reservation);
            billService.updateBill(bill);

            response.sendRedirect("reservationController?command=user_cabinet_page");
//            TODO Logger...
        } catch (ServiceException e) {
            System.err.println(e);
            response.sendRedirect("error?errorMessage=Ooops, something went wrong");
        }
    }
}
