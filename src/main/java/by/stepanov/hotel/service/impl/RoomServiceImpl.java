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
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class RoomServiceImpl implements RoomService {

    private final static String SAVE_IMAGE_DIRECTORY = "images\\rooms";
    public static final String ANY_TYPE = "Any type";

    private static final Logger log = Logger.getLogger(RoomServiceImpl.class);

    private RoomDao roomDao = DaoProvider.getInstance().getRoomDao();
    private ReservationService reservationService = ServiceProvider.getReservationService();

    @Override
    public void createRoom(Room room) throws ServiceException {
        try {
            roomDao.createRoom(room);
            log.info("Room with number: '" + room.getRoomNumber() + "' created");
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Room readRoom(long roomId) throws ServiceException {
        try {
            log.info("Room with id: '" + roomId + "' read");
            return roomDao.readRoom(roomId);
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateRoom(Room room) throws ServiceException {
        try {
            roomDao.updateRoom(room);
            log.info("Room with id: '" + room.getId() + "' updated");
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteRoom(long roomId) throws ServiceException {
        try {
            roomDao.deleteRoom(roomId);
            log.info("Room with id: '" + roomId + "' deleted");
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Room> getAllRooms() throws ServiceException {
        try {
            log.info("All rooms read");
            return roomDao.getAllRooms();
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Room> getFreeRooms(String inDate, String outDate, String roomType) throws ServiceException {

        LocalDate inLocalDate = LocalDate.parse(inDate);
        LocalDate outLocalDate = LocalDate.parse(outDate);

        List<Reservation> reservations = reservationService.getReservationsByAfterDate(String.valueOf(LocalDate.now()));
        List<Room> rooms = getAllRooms();

        if (!ANY_TYPE.equals(roomType)) {
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

        log.info("Get free rooms with room type '" + roomType + "' and from '" + inDate + "' to '" + outDate + "'");

        return rooms;
    }
}
