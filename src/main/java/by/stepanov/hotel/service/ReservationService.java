package by.stepanov.hotel.service;

import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface ReservationService {

    void createReservation(Reservation reservation) throws ServiceException;

    Reservation readReservation(long reservationId) throws ServiceException;

    void updateReservation(Reservation reservation) throws ServiceException;

    void deleteReservation(long reservationId) throws ServiceException;

    List<Reservation> getAllReservations() throws ServiceException;

    List<Reservation> getReservationsByAfterDate(String date) throws ServiceException;

    List<Reservation> getReservationsByUser(User user) throws ServiceException;

    List<Reservation> getReservationByPeriod(String fromDate, String untilDate) throws ServiceException;

    List<Reservation> getActualUserReservations(User attribute) throws ServiceException;
}
