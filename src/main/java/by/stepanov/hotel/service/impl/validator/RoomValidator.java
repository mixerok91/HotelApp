package by.stepanov.hotel.service.impl.validator;

import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import org.apache.log4j.Logger;

public class RoomValidator {

    private static final Logger log = Logger.getLogger(RoomValidator.class);

    private static RoomService roomService = ServiceProvider.getRoomService();

    public static boolean isRoomNameAppropriate(Room room) throws ServiceException {
        boolean nameAppropriate = true;
        try {

            if (room.getId() != null){
                if(roomService.readRoom(room.getId()).getRoomNumber().equals(room.getRoomNumber())){
                    log.info("Room type with number: '" + room.getRoomNumber() + "' valid");
                    return true;
                }
            }

            if (roomService.getAllRooms().stream()
                    .anyMatch(r -> r.getRoomNumber().equals(room.getRoomNumber()))){
                log.info("Room type with number: '" + room.getRoomNumber() + "' not valid");
                return false;
            }
        } catch (ServiceException e) {
            log.error("Service exception",e);
            throw new ServiceException(e);
        }
        return nameAppropriate;
    }

}
