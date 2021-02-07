package by.stepanov.hotel.controller;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminController", urlPatterns = "/adminController")
public class AdminController extends HttpServlet {
    private final CommandProvider commandProvider = new CommandProvider();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String currentCommandName;
        Command command;

        currentCommandName = request.getParameter("command");
        command = commandProvider.getCommand(currentCommandName);
        command.execute(request, response);
    }
}
