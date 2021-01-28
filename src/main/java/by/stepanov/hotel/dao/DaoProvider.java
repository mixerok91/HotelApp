package by.stepanov.hotel.dao;

import by.stepanov.hotel.dao.impl.mysql.*;

public class DaoProvider {

    private final static DaoProvider instance = new DaoProvider();

    private final static UserDao userDao = new UserDaoImpl();
    private final static RoomTypeDao roomTypeDao = new RoomTypeDaoImpl();
    private final static RoomDao roomDao = new RoomDaoImpl();
    private final static ReservationDao reservationDao = new ReservationDaoImpl();
    private final static BillDao billDao = new BillDaoImpl();

    private DaoProvider(){};

    public static DaoProvider getInstance(){
        return instance;
    }

    public UserDao getUserDao(){
        return userDao;
    }

    public RoomTypeDao getRoomTypeDao() {
        return roomTypeDao;
    }

    public RoomDao getRoomDao() {
        return roomDao;
    }

    public ReservationDao getReservationDao() {
        return reservationDao;
    }

    public BillDao getBillDao() {
        return billDao;
    }
}
