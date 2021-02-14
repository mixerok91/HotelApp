package by.stepanov.hotel.controller.command.impl;

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
import java.time.LocalDate;

public class Login implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        request.setAttribute("email", email);

        String emailError;
        String passwordError;
        boolean hasErrors = false;

        UserService userService = ServiceProvider.getInstance().getUserService();

        try {
            if (!UserParamsValidator.emailValid(email)){
                emailError = "Email is not valid";
                request.setAttribute("emailError", emailError);
                hasErrors = true;
            }

            User user = userService.readUser(email);

            if (user == null){
                emailError = "User with that email does not exist";
                request.setAttribute("emailError", emailError);
                hasErrors = true;
            }
            if (!UserParamsValidator.passwordValid(password)){
                passwordError = "Password is not valid";
                request.setAttribute("passwordError", passwordError);
                hasErrors = true;
            }

            if (user!=null && !BCrypt.checkpw(password, user.getPassword())){
                passwordError = "That user has other password";
                request.setAttribute("passwordError", passwordError);
                hasErrors = true;
            }

            if (hasErrors){
                request.getRequestDispatcher("/login").forward(request,response);
            }

            user.setLastInDate(LocalDate.now());
            userService.updateUser(user);

            request.getSession().setAttribute("user", user);

            response.sendRedirect("mainController?command=main_page");
        } catch (ServiceException e) {
//            TODO logger
            response.sendRedirect("error?errorMessage=Ooops, something went wrong, with logging.");
        }
    }
}
