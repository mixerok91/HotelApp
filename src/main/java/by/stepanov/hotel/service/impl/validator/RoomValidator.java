package by.stepanov.hotel.service.impl.validator;

import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

public class RoomValidator {

    private static RoomService roomService = ServiceProvider.getRoomService();

    public static boolean isRoomNameAppropriate(Room room) throws ServiceException {
        boolean nameAppropriate = true;
        try {

            if (room.getId() != null){
                if(roomService.readRoom(room.getId()).getRoomNumber().equals(room.getRoomNumber())){
                    return true;
                }
            }

            if (roomService.getAllRooms().stream()
                    .anyMatch(r -> r.getRoomNumber().equals(room.getRoomNumber()))){
                return false;
            }
        } catch (ServiceException e) {
//            TODO logger
            throw new ServiceException(e);
        }
        return nameAppropriate;
    }

}
