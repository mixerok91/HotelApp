package by.stepanov.hotel.service.impl;

import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.DaoProvider;
import by.stepanov.hotel.dao.ReservationDao;
import by.stepanov.hotel.entity.BookStatus;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.BillService;
import by.stepanov.hotel.service.ReservationService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationServiceImpl implements ReservationService {

    private ReservationDao reservationDao = DaoProvider.getInstance().getReservationDao();
    private BillService billService = ServiceProvider.getBillService();

    @Override
    public void createReservation(Reservation reservation) throws ServiceException {
        try {
            reservationDao.createReservation(reservation);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Reservation readReservation(long reservationId) throws ServiceException {
        try {
            Reservation reservation = reservationDao.readReservation(reservationId);
            reservation.setBill(billService.readBillByReservationId(reservationId));
            return reservationDao.readReservation(reservationId);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateReservation(Reservation reservation) throws ServiceException {
        try {
            reservationDao.updateReservation(reservation);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteReservation(long reservationId) throws ServiceException {
        try {
            reservationDao.deleteReservation(reservationId);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Reservation> getAllReservations() throws ServiceException {
        try {
            List<Reservation> reservations = reservationDao.getAllReservations();
            return reservations;
        }catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Reservation> getReservationsByAfterDate(String date) throws ServiceException {
        try {
            return reservationDao.getReservationsByAfterDate(date);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Reservation> getReservationsByUser(User user) throws ServiceException {
        try {
            List<Reservation> reservations = reservationDao.getReservationsByUser(user);
            return reservations;
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Reservation> getReservationByPeriod(String fromDate, String beforeDate) throws ServiceException {
        List<Reservation> reservationList = null;
        try {
            reservationList = reservationDao.getReservationsByAfterDate(fromDate);
            reservationList = reservationList.stream()
                    .filter(reservation -> reservation.getInDate().isBefore(LocalDate.parse(beforeDate)))
                    .collect(Collectors.toList());
        } catch (DAOException e) {
//            TODO Logger
            throw new ServiceException(e);
        }
        return reservationList;
    }

    @Override
    public List<Reservation> getActualUserReservations(User user) throws ServiceException {
        List<Reservation> userReservations = getReservationsByUser(user);
        userReservations.removeIf(reservation -> reservation.getBookStatus().equals(BookStatus.CANCELLED)
                ||reservation.getBookStatus().equals(BookStatus.FINISHED)
                ||reservation.getInDate().isBefore(LocalDate.now()));
        return userReservations;
    }
}
