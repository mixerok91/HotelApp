package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.RoomType;
import by.stepanov.hotel.service.RoomTypeService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import by.stepanov.hotel.service.impl.validator.RoomTypeValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditRoomType implements Command {

    public static final String ROOM_TYPE_ID = "roomTypeId";
    public static final String TYPE_NAME = "typeName";
    public static final String RUSSIAN_DESCRIPTION = "russianDescription";
    public static final String ENGLISH_DESCRIPTION = "englishDescription";
    public static final String MESSAGE = "message";
    public static final String ROOM_TYPE_ADMINISTRATION_PAGE_CONTROLLER = "adminController?command=room_type_administration_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RoomTypeService roomTypeService = ServiceProvider.getRoomTypeService();

        RoomType roomType = new RoomType();

        try {
            roomType.setId(Long.valueOf(request.getParameter(ROOM_TYPE_ID)));
            roomType.setTypeName(request.getParameter(TYPE_NAME));
            roomType.setDescriptionRus(request.getParameter(RUSSIAN_DESCRIPTION));
            roomType.setDescriptionEng(request.getParameter(ENGLISH_DESCRIPTION));

            if (RoomTypeValidator.isRoomTypeNameAppropriate(roomType)){
                roomTypeService.updateRoomType(roomType);
                request.setAttribute(MESSAGE, "RoomType '" + roomType.getTypeName() + "' updated");
            } else {
                request.setAttribute(MESSAGE, "RoomType '" + roomType.getTypeName() + "' already exist");
            }
        } catch (ServiceException e) {
            request.setAttribute(MESSAGE, "RoomType '" + roomType.getTypeName() + "' did not update");
            System.err.println(e);
//            TODO logger
        }

        request.getRequestDispatcher(ROOM_TYPE_ADMINISTRATION_PAGE_CONTROLLER).forward(request, response);
    }
}
