package by.stepanov.hotel.service.schedule;

import by.stepanov.hotel.entity.BookStatus;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.service.ReservationService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CheckReservationStatusJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        ReservationService reservationService = ServiceProvider.getReservationService();

        try {
            List<Reservation> reservations = reservationService.getReservationsByAfterDate(LocalDate.now().toString());

            reservations = reservations.stream()
                    .filter(reservation -> reservation.getBookStatus().equals(BookStatus.RESERVED))
                    .filter(reservation -> reservation.getInDate().isEqual(LocalDate.now().plusDays(1)))
                    .collect(Collectors.toList());
            reservations.forEach(reservation -> reservation.setBookStatus(BookStatus.CANCELLED));

//        TODO Logger
            for (Reservation reservation : reservations) {
                reservationService.updateReservation(reservation);
            }

        } catch (ServiceException e) {
//        TODO Logger
            e.printStackTrace();
        }
    }
}
