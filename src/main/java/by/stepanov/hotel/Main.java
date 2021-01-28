package by.stepanov.hotel;

import by.stepanov.hotel.dao.*;
import by.stepanov.hotel.entity.BookStatus;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) throws DAOException, SQLException, ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        RoomDao roomDao = daoProvider.getRoomDao();
        UserDao userDao = daoProvider.getUserDao();
        ReservationDao reservationDao = daoProvider.getReservationDao();
        RoomService roomService = ServiceProvider.getRoomService();

        List<Room> rooms = roomService.getFreeRooms("2021-01-02","2021-02-01","Suite");

        rooms.forEach(room -> System.out.println(room));
    }
}
