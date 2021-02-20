package by.stepanov.hotel.service;

import by.stepanov.hotel.entity.RoomType;

import java.util.List;

public interface RoomTypeService {

    void createRoomType(RoomType roomType) throws ServiceException;

    RoomType readRoomType(long roomTypeId) throws ServiceException;

    void updateRoomType(RoomType roomType) throws ServiceException;

    void deleteRoomType(long roomTypeId) throws ServiceException;

    List<RoomType> getAllRoomTypes() throws ServiceException;

    RoomType readRoomType(String roomType) throws ServiceException;
}
