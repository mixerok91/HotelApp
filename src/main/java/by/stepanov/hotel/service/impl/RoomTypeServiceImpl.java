package by.stepanov.hotel.service.impl;

import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.DaoProvider;
import by.stepanov.hotel.dao.RoomTypeDao;
import by.stepanov.hotel.entity.RoomType;
import by.stepanov.hotel.service.RoomTypeService;
import by.stepanov.hotel.service.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class RoomTypeServiceImpl implements RoomTypeService {
//TODO Logger in every methods
    private RoomTypeDao roomTypeDao = DaoProvider.getInstance().getRoomTypeDao();

    @Override
    public void createRoomType(RoomType roomType) throws ServiceException {
        try {
            roomTypeDao.createRoomType(roomType);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public RoomType readRoomType(long roomTypeId) throws ServiceException {

        try {
            return roomTypeDao.readRoomType(roomTypeId);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateRoomType(RoomType roomType) throws ServiceException {
        try {
            roomTypeDao.updateRoomType(roomType);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteRoomType(long roomTypeId) throws ServiceException {
        try {
            roomTypeDao.deleteRoomType(roomTypeId);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<RoomType> getAllRoomTypes() throws ServiceException {
        try {
            return roomTypeDao.getAllRoomTypes();
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public RoomType readRoomType(String roomTypeName) throws ServiceException {
        RoomType roomType = null;
        try {
            roomType = roomTypeDao.getAllRoomTypes().stream()
                    .filter(roomType1 -> roomType1.getTypeName().equals(roomTypeName))
                    .findFirst().get();
        } catch (DAOException e) {
//            TODO Logger
            throw new ServiceException(e);
        }
        return roomType;
    }
}
