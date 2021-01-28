package by.stepanov.hotel.controller.command.impl;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Role;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import by.stepanov.hotel.service.UserService;
import by.stepanov.hotel.service.impl.validator.UserParamsValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Registration implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String emailError;
        String passwordError;
        String firsNameError;
        String surNameError;

        boolean hasErrors = false;

        UserService userService = ServiceProvider.getInstance().getUserService();

        try {
            User user = new User();
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            user.setFirstName(request.getParameter("firstName"));
            user.setSurName(request.getParameter("surName"));
            user.setRole(Role.USER);

            request.setAttribute("user", user);

            if (!UserParamsValidator.emailValid(user.getEmail())){
                emailError = "Email is not valid";
                request.setAttribute("emailError", emailError);
                hasErrors = true;
            }
            if (userService.readUser(user.getEmail()) != null){
                emailError = "User with that email already exist";
                request.setAttribute("emailError", emailError);
                hasErrors = true;
            }
            if (!UserParamsValidator.passwordValid(user.getPassword())){
                passwordError = "Password is not valid";
                request.setAttribute("passwordError", passwordError);
                hasErrors = true;
            }
            if (!UserParamsValidator.nameValid(user.getFirstName())){
                firsNameError = "First name is not valid";
                request.setAttribute("firsNameError", firsNameError);
                hasErrors = true;
            }
            if (!UserParamsValidator.nameValid(user.getSurName())){
                surNameError = "Surname is not valid";
                request.setAttribute("surNameError", surNameError);
                hasErrors = true;
            }

            if (hasErrors){
                request.getRequestDispatcher("/registration").forward(request,response);
            } else {
                ServiceProvider.getInstance().getUserService().createUser(user);
                response.sendRedirect("userController?command=login_page");
            }

        } catch (ServiceException e) {
            response.sendRedirect("error?errorMessage=Ooops, something went wrong, with registration.");
        }
    }
}
