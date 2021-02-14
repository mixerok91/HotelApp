package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.RoomType;
import by.stepanov.hotel.service.RoomTypeService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateRoomType implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RoomType roomType = new RoomType();
        roomType.setTypeName(request.getParameter("typeName"));
        roomType.setDescriptionRus(request.getParameter("russianDescription"));
        roomType.setDescriptionEng(request.getParameter("englishDescription"));

        RoomTypeService roomTypeService = ServiceProvider.getRoomTypeService();

        try {
            boolean nameNotUnique = roomTypeService.getAllRoomTypes().stream()
                    .anyMatch(rt -> rt.getTypeName().equals(roomType.getTypeName()));
            if (!nameNotUnique){
                roomTypeService.createRoomType(roomType);
                request.setAttribute("message", "RoomType '" + roomType.getTypeName() + "' created");
            } else {
                request.setAttribute("message", "RoomType '" + roomType.getTypeName() + "' already exist");
            }
        } catch (ServiceException e) {
            request.setAttribute("message", "RoomType '" + roomType.getTypeName() + "' did not create");
            System.err.println(e);
//            TODO logger
        }
    request.getRequestDispatcher("adminController?command=room_type_administration_page").forward(request, response);
    }
}
