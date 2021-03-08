package by.stepanov.hotel.controller.command.impl;

import by.stepanov.hotel.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLocale implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String lang = request.getParameter("lang");
        request.getSession().setAttribute("localization", lang);

        response.sendRedirect(String.valueOf(request.getSession().getAttribute("lastPath")));
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
