package by.stepanov.hotel.service;

import by.stepanov.hotel.service.impl.*;

public class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private static final UserService userService = new UserServiceImpl();
    private static final RoomTypeService roomTypeService = new RoomTypeServiceImpl();
    private static final ReservationService reservationService = new ReservationServiceImpl();
    private static final RoomService roomService = new RoomServiceImpl();
    private static final BillService billService = new BillServiceImpl();

    private ServiceProvider(){}

    public static ServiceProvider getInstance(){
        return instance;
    }

    public static UserService getUserService(){
        return userService;
    }

    public static RoomTypeService getRoomTypeService() {
        return roomTypeService;
    }

    public static ReservationService getReservationService() {
        return reservationService;
    }

    public static RoomService getRoomService() {
        return roomService;
    }

    public static BillService getBillService() {
        return billService;
    }
}
