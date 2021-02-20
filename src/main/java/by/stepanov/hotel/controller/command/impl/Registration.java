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
import java.util.Map;

public class Registration implements Command {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "firstName";
    public static final String SUR_NAME = "surName";
    public static final String USER = "user";
    public static final String ERRORS = "errors";
    public static final String REGISTRATION = "/registration";
    public static final String LOGIN_PAGE_CONTROLLER = "userController?command=login_page";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, with registration.";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        UserService userService = ServiceProvider.getUserService();
        UserParamsValidator userParamsValidator = new UserParamsValidator();

        try {
            User user = new User();
            user.setEmail(request.getParameter(EMAIL));
            user.setPassword(request.getParameter(PASSWORD));
            user.setFirstName(request.getParameter(FIRST_NAME));
            user.setSurName(request.getParameter(SUR_NAME));
            user.setRole(Role.USER);

            request.setAttribute(USER, user);

            Map<String, String> errors = userParamsValidator.validateParamsForRegistration(user);

            if (!errors.isEmpty()){
                request.setAttribute(ERRORS, errors);
                request.getRequestDispatcher(REGISTRATION).forward(request,response);
            } else {
                userService.createUser(user);
                response.sendRedirect(LOGIN_PAGE_CONTROLLER);
            }

        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE);
        }
    }
}
