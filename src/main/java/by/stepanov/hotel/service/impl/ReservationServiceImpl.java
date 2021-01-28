package by.stepanov.hotel.service.impl;

import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.DaoProvider;
import by.stepanov.hotel.dao.ReservationDao;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.ReservationService;
import by.stepanov.hotel.service.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {

    private ReservationDao reservationDao = DaoProvider.getInstance().getReservationDao();

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
            return reservationDao.getAllReservations();
        } catch (DAOException e) {
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
            return reservationDao.getReservationsByUser(user);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }
}
