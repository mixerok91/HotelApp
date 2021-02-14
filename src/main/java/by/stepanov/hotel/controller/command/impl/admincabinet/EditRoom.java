package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.entity.RoomType;
import by.stepanov.hotel.service.RoomService;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class EditRoom implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RoomService roomService = ServiceProvider.getRoomService();

        Room room = new Room();
        room.setId(Long.valueOf(request.getParameter("roomId")));
        room.setPersons(Integer.parseInt(request.getParameter("personsNumber")));
        room.setDayCost(Double.parseDouble(request.getParameter("costPerDay")));
        room.setRoomNumber(request.getParameter("roomNumber"));

        List<RoomType> roomTypes = (List<RoomType>) request.getSession().getAttribute("roomTypes");
        room.setRoomType(roomTypes.stream()
                .filter(roomType -> roomType.getTypeName().equals(request.getParameter("roomType")))
                .findFirst().get());

        room.setPicturePath(request.getParameter("picturePath"));

        try {
            roomService.saveUploadedFileAndAddPathToRoom(request, room);

            boolean nameNotUnique = roomService.getAllRooms().stream()
                    .anyMatch(r -> r.getRoomNumber().equals(room.getRoomNumber()) && !r.getId().equals(room.getId()));
            if (!nameNotUnique){
                roomService.updateRoom(room);
                request.setAttribute("message", "Room '" + room.getRoomNumber() + "' updated");
            } else {
                request.setAttribute("message", "Room '" + room.getRoomNumber() + "' already exist");
            }
        } catch (ServiceException e) {
            request.setAttribute("message", "Room '" + room.getRoomNumber() + "' did not create");
            System.err.println(e);
//            TODO logger
        }
        request.getRequestDispatcher("adminController?command=room_administration_page").forward(request, response);
    }
}
