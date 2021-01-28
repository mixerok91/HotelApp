package by.stepanov.hotel.service.impl;

import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.DaoProvider;
import by.stepanov.hotel.dao.RoomDao;
import by.stepanov.hotel.entity.BookStatus;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.service.ReservationService;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomServiceImpl implements RoomService {

    private RoomDao roomDao = DaoProvider.getInstance().getRoomDao();
    private ReservationService reservationService = ServiceProvider.getReservationService();

    @Override
    public void createRoom(Room room) throws ServiceException {
        try {
            roomDao.createRoom(room);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Room readRoom(long roomId) throws ServiceException {
        try {
            return roomDao.readRoom(roomId);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateRoom(Room room) throws ServiceException {
        try {
            roomDao.updateRoom(room);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteRoom(long roomId) throws ServiceException {
        try {
            roomDao.deleteRoom(roomId);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Room> getAllRooms() throws ServiceException {
        try {
            return roomDao.getAllRooms();
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Room> getFreeRooms(String inDate, String outDate, String roomType) throws ServiceException {

        LocalDate inLocalDate = LocalDate.parse(inDate);
        LocalDate outLocalDate = LocalDate.parse(outDate);

        List<Reservation> reservations = reservationService.getReservationsByAfterDate(String.valueOf(LocalDate.now()));
        List<Room> rooms = getAllRooms();

        if (!"Any type".equals(roomType)) {
            rooms.removeIf(room -> !roomType.equals(room.getRoomType().getTypeName()));
        }

        for (Reservation reservation : reservations) {
            if (outLocalDate.isAfter(reservation.getInDate()) && outLocalDate.isBefore(reservation.getOutDate())
            && (reservation.getBookStatus().equals(BookStatus.PAID_FOR)||reservation.getBookStatus().equals(BookStatus.RESERVED))){
                rooms.remove(reservation.getRoom());
                continue;
            }
            if (inLocalDate.isAfter(reservation.getInDate()) && inLocalDate.isBefore(reservation.getOutDate())
            && (reservation.getBookStatus().equals(BookStatus.PAID_FOR)||reservation.getBookStatus().equals(BookStatus.RESERVED))){
                rooms.remove(reservation.getRoom());
                continue;
            }
            if (inLocalDate.isBefore(reservation.getInDate()) && outLocalDate.isAfter(reservation.getOutDate())
            && (reservation.getBookStatus().equals(BookStatus.PAID_FOR)||reservation.getBookStatus().equals(BookStatus.RESERVED))){
                rooms.remove(reservation.getRoom());
                continue;
            }
            if ((inLocalDate.isEqual(reservation.getInDate()) || outLocalDate.isEqual(reservation.getOutDate()))
                    && (reservation.getBookStatus().equals(BookStatus.PAID_FOR)||reservation.getBookStatus().equals(BookStatus.RESERVED))){
                rooms.remove(reservation.getRoom());
            }
        }

        return rooms;
    }
}
