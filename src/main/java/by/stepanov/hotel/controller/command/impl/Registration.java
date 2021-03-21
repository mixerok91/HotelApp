package by.stepanov.hotel.controller.command.impl;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.Role;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import by.stepanov.hotel.service.UserService;
import by.stepanov.hotel.service.impl.validator.UserParamsValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class Registration implements Command {

    private static final Logger log = Logger.getLogger(Registration.class);

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstName";
    private static final String SUR_NAME = "surName";
    private static final String USER = "user";
    private static final String ERRORS = "errors";
    private static final String REGISTRATION_PAGE = "/registration";
    private static final String LOGIN_PAGE = "login";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, with registration.";

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
                log.info("Dispatched to " + REGISTRATION_PAGE + " with error");
                request.getRequestDispatcher(REGISTRATION_PAGE).forward(request,response);
            } else {
                userService.createUser(user);
                log.info("Redirect to: " + LOGIN_PAGE);
                response.sendRedirect(LOGIN_PAGE);
            }
        } catch (ServiceException e) {
            log.info("Redirect to error page");
            response.sendRedirect(ERROR_PAGE);
        }
    }

    @Override
    public void savePathToSession(HttpServletRequest request) {
    }
}
