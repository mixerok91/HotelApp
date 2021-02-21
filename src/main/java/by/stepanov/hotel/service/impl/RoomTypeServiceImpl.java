package by.stepanov.hotel.service.impl;

import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.DaoProvider;
import by.stepanov.hotel.dao.RoomTypeDao;
import by.stepanov.hotel.entity.RoomType;
import by.stepanov.hotel.service.RoomTypeService;
import by.stepanov.hotel.service.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class RoomTypeServiceImpl implements RoomTypeService {

    private static final Logger log = Logger.getLogger(RoomTypeServiceImpl.class);

    private RoomTypeDao roomTypeDao = DaoProvider.getInstance().getRoomTypeDao();

    @Override
    public void createRoomType(RoomType roomType) throws ServiceException {
        try {
            roomTypeDao.createRoomType(roomType);
            log.info("Room type with id: '" + roomType.getId() + "' created");
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public RoomType readRoomType(long roomTypeId) throws ServiceException {

        try {
            log.info("Room type with id: '" + roomTypeId + "' read");
            return roomTypeDao.readRoomType(roomTypeId);
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateRoomType(RoomType roomType) throws ServiceException {
        try {
            roomTypeDao.updateRoomType(roomType);
            log.info("Room type with id: '" + roomType.getId() + "' updated");
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteRoomType(long roomTypeId) throws ServiceException {
        try {
            roomTypeDao.deleteRoomType(roomTypeId);
            log.info("Room type with id: '" + roomTypeId + "' deleted");
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<RoomType> getAllRoomTypes() throws ServiceException {
        try {
            log.info("All room types read");
            return roomTypeDao.getAllRoomTypes();
        } catch (DAOException e) {
            log.error("DAO exception",e);
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
            log.info("Room type with with name: '" + roomType.getTypeName() + "' read");
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
        return roomType;
    }
}
