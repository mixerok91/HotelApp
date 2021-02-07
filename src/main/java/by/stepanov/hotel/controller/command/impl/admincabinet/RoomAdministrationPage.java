package by.stepanov.hotel.controller.command.impl.admincabinet;

import by.stepanov.hotel.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoomAdministrationPage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect("roomAdministration");
    }
}
