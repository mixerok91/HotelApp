package by.stepanov.hotel.controller.command.impl.usercabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import by.stepanov.hotel.service.UserService;
import by.stepanov.hotel.service.impl.validator.UserParamsValidator;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserData implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String passwordError;
        String firsNameError;
        String surNameError;

        boolean hasErrors = false;

        UserService userService = ServiceProvider.getInstance().getUserService();

        try {
            User currentUser = (User) request.getSession().getAttribute("user");
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String firstName = request.getParameter("firstName");
            String surName = request.getParameter("surName");

            if (oldPassword!=null) {
                if (!BCrypt.checkpw(oldPassword, currentUser.getPassword())) {
                    passwordError = "Old password incorrect";
                    request.setAttribute("oldPasswordError", passwordError);
                    hasErrors = true;
                }
            }
            if (newPassword!=null) {
                if (!UserParamsValidator.passwordValid(newPassword)) {
                    passwordError = "New password is not valid";
                    request.setAttribute("newPasswordValidationError", passwordError);
                    hasErrors = true;
                }
                if (BCrypt.checkpw(newPassword, currentUser.getPassword())) {
                    passwordError = "The new password must be different from the old one";
                    request.setAttribute("newPasswordValidationError", passwordError);
                    hasErrors = true;
                }
            }
            if (firstName!=null) {
                if (!UserParamsValidator.nameValid(firstName)) {
                    firsNameError = "New first name is not valid";
                    request.setAttribute("firsNameError", firsNameError);
                    hasErrors = true;
                }
            }
            if (surName!=null) {
                if (!UserParamsValidator.nameValid(surName)) {
                    surNameError = "New surname is not valid";
                    request.setAttribute("surNameError", surNameError);
                    hasErrors = true;
                }
            }

            if (hasErrors) {
                request.getRequestDispatcher("/editUserData").forward(request, response);
            } else {
                currentUser.setPassword(BCrypt.hashpw(newPassword,BCrypt.gensalt(12)));
                currentUser.setFirstName(firstName);
                currentUser.setSurName(surName);
                ServiceProvider.getInstance().getUserService().updateUser(currentUser);
                request.getSession().setAttribute("user", currentUser);
                response.sendRedirect("reservationController?command=user_cabinet_page");
            }

        } catch (ServiceException e) {
            response.sendRedirect("error?errorMessage=Ooops, something went wrong, with registration.");
        }
    }
}
