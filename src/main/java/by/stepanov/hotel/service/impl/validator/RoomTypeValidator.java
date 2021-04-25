package by.stepanov.hotel.service.impl.validator;

import by.stepanov.hotel.entity.RoomType;
import by.stepanov.hotel.service.RoomTypeService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import org.apache.log4j.Logger;

public class RoomTypeValidator {

    private static final Logger log = Logger.getLogger(RoomTypeValidator.class);

    private static RoomTypeService roomTypeService = ServiceProvider.getRoomTypeService();

    public static boolean isRoomTypeNameAppropriate(RoomType roomType) throws ServiceException {

        boolean nameAppropriate = true;

        try {
            if (roomType.getId() != null){
                if(roomTypeService.readRoomType(roomType.getId()).getTypeName().equals(roomType.getTypeName())){
                    log.info("Room type with name: '" + roomType.getTypeName() + "' valid");
                    return true;
                }
            }

            if (roomTypeService.getAllRoomTypes().stream()
                    .anyMatch(r -> r.getTypeName().equals(roomType.getTypeName()))){
                log.info("Room type with name: '" + roomType.getTypeName() + "' not valid");
                return false;
            }
        } catch (ServiceException e) {
            log.error("Service exception",e);
            throw new ServiceException(e);
        }
        return nameAppropriate;
    }
}
