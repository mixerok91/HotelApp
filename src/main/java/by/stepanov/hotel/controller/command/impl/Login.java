package by.stepanov.hotel.controller.command.impl;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import by.stepanov.hotel.service.UserService;
import by.stepanov.hotel.service.impl.validator.UserParamsValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class Login implements Command {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String USER = "user";
    public static final String MAIN_PAGE_CONTROLLER = "mainController?command=main_page";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, with logging.";
    public static final String ERRORS = "errors";
    public static final String LOGIN = "/login";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        request.setAttribute(EMAIL, email);

        UserService userService = ServiceProvider.getUserService();
        UserParamsValidator userParamsValidator = new UserParamsValidator();

        try {
            Map<String, String> errors = userParamsValidator.validateParamsForLogin(email, password);
            if (!errors.isEmpty()){
                request.setAttribute(ERRORS, errors);
                request.getRequestDispatcher(LOGIN).forward(request,response);
            } else {
                User user = userService.readUser(email);
                user.setLastInDate(LocalDate.now());
                userService.updateUser(user);
                request.getSession().setAttribute(USER, user);
                response.sendRedirect(MAIN_PAGE_CONTROLLER);
            }
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {

    }
}
