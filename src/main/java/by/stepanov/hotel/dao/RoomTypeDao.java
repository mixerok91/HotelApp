package by.stepanov.hotel.dao;

import by.stepanov.hotel.entity.RoomType;

import java.util.List;

public interface RoomTypeDao {

    void createRoomType(RoomType roomType) throws DAOException;

    RoomType readRoomType(long roomTypeId) throws DAOException;

    void updateRoomType(RoomType roomType) throws DAOException;

    void deleteRoomType(long roomTypeId) throws DAOException;

    List<RoomType> getAllRoomTypes() throws DAOException;
}
