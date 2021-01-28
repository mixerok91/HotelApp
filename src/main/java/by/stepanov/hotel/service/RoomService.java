package by.stepanov.hotel.service;

import by.stepanov.hotel.entity.Room;

import java.util.List;

public interface RoomService {

    void createRoom(Room room) throws ServiceException;

    Room readRoom(long roomId) throws ServiceException;

    void updateRoom(Room room) throws ServiceException;

    void deleteRoom(long roomId) throws ServiceException;

    List<Room> getAllRooms() throws ServiceException;

    List<Room> getFreeRooms(String inDate, String outDate, String roomType) throws ServiceException;
}
