package by.stepanov.hotel.dao;

import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.User;

import java.util.List;

public interface ReservationDao {

    void createReservation(Reservation reservation) throws DAOException;

    Reservation readReservation(long reservationId) throws DAOException;

    void updateReservation(Reservation reservation) throws DAOException;

    void deleteReservation(long reservationId) throws DAOException;

    List<Reservation> getAllReservations() throws DAOException;

    List<Reservation> getReservationsByAfterDate(String date) throws DAOException;

    List<Reservation> getReservationsByUser(User user) throws DAOException;
}
