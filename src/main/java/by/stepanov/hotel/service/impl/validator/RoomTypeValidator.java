package by.stepanov.hotel.service.impl.validator;

import by.stepanov.hotel.entity.RoomType;
import by.stepanov.hotel.service.RoomTypeService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

public class RoomTypeValidator {

    private  static RoomTypeService roomTypeService = ServiceProvider.getRoomTypeService();

    public static boolean isRoomTypeNameAppropriate(RoomType roomType) throws ServiceException {
        boolean nameAppropriate = true;

        try {
            if (roomType.getId() != null){
                if(roomTypeService.readRoomType(roomType.getId()).getTypeName().equals(roomType.getTypeName())){
                    return true;
                }
            }

            if (roomTypeService.getAllRoomTypes().stream()
                    .anyMatch(r -> r.getTypeName().equals(roomType.getTypeName()))){
                return false;
            }
        } catch (ServiceException e) {
//            TODO logger
            throw new ServiceException(e);
        }
        return nameAppropriate;
    }
}
