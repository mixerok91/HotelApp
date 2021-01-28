package by.stepanov.hotel.dao;

import by.stepanov.hotel.entity.Room;

import java.util.List;

public interface RoomDao {

    void createRoom(Room room) throws DAOException;

    Room readRoom(long roomId) throws DAOException;

    void updateRoom(Room room) throws DAOException;

    void deleteRoom(long roomId) throws DAOException;

    List<Room> getAllRooms() throws DAOException;
}
