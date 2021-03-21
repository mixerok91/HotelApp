package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.RoomType;
import by.stepanov.hotel.service.RoomTypeService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import by.stepanov.hotel.service.impl.validator.RoomTypeValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditRoomType implements Command {

    private static final Logger log = Logger.getLogger(EditRoomType.class);

    private static final String ROOM_TYPE_ID = "roomTypeId";
    private static final String TYPE_NAME = "typeName";
    private static final String RUSSIAN_DESCRIPTION = "russianDescription";
    private static final String ENGLISH_DESCRIPTION = "englishDescription";
    private static final String MESSAGE = "message";
    private static final String ROOM_TYPE_ADMINISTRATION_PAGE_CONTROLLER = "mainController?command=room_type_administration_page";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, try later";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RoomTypeService roomTypeService = ServiceProvider.getRoomTypeService();

        try {
            RoomType roomType = new RoomType();

            roomType.setId(Long.valueOf(request.getParameter(ROOM_TYPE_ID)));
            roomType.setTypeName(request.getParameter(TYPE_NAME));
            roomType.setDescriptionRus(request.getParameter(RUSSIAN_DESCRIPTION));
            roomType.setDescriptionEng(request.getParameter(ENGLISH_DESCRIPTION));

            if (RoomTypeValidator.isRoomTypeNameAppropriate(roomType)){
                roomTypeService.updateRoomType(roomType);
                log.info("Redirect to: " + ROOM_TYPE_ADMINISTRATION_PAGE_CONTROLLER);
                response.sendRedirect(ROOM_TYPE_ADMINISTRATION_PAGE_CONTROLLER);
            } else {
                request.setAttribute(MESSAGE, "RoomType '" + roomType.getTypeName() + "' already exist");
                log.info("Dispatched to " + ROOM_TYPE_ADMINISTRATION_PAGE_CONTROLLER + " with error");
                request.getRequestDispatcher(ROOM_TYPE_ADMINISTRATION_PAGE_CONTROLLER).forward(request, response);
            }
        } catch (ServiceException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
