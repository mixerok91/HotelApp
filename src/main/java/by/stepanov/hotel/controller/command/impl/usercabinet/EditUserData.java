package by.stepanov.hotel.controller.command.impl.usercabinet;

import by.stepanov.hotel.controller.command.Command;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import by.stepanov.hotel.service.UserService;
import by.stepanov.hotel.service.impl.validator.UserParamsValidator;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class EditUserData implements Command {

    private static final Logger log = Logger.getLogger(EditUserData.class);

    private static final String LOGIN_PAGE = "mainController?command=login_page";
    private static final String USER = "user";
    private static final String OLD_PASSWORD = "oldPassword";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String FIRST_NAME = "firstName";
    private static final String SUR_NAME = "surName";
    private static final String ERRORS = "errors";
    private static final String EDIT_USER_DATA = "/editUserData";
    private static final String USER_CABINET_PAGE_CONTROLLER = "mainController?command=user_cabinet_page";
    private static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, with registration.";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            log.info("Redirected to login page because session does not exist");
            response.sendRedirect(LOGIN_PAGE);
        }

        UserParamsValidator userParamsValidator = new UserParamsValidator();
        UserService userService = ServiceProvider.getUserService();

        try {
            User currentUser = (User) request.getSession().getAttribute(USER);
            String oldPassword = request.getParameter(OLD_PASSWORD);
            String newPassword = request.getParameter(NEW_PASSWORD);
            String firstName = request.getParameter(FIRST_NAME);
            String surName = request.getParameter(SUR_NAME);

            Map<String, String> errors = userParamsValidator.validateParamsForUserEditing(currentUser, oldPassword, newPassword, firstName, surName);

            if (!errors.isEmpty()){
                request.setAttribute(ERRORS, errors);
                log.info("Dispatched to " + EDIT_USER_DATA + " with error");
                request.getRequestDispatcher(EDIT_USER_DATA).forward(request, response);
            } else {
                currentUser.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
                currentUser.setFirstName(firstName);
                currentUser.setSurName(surName);
                userService.updateUser(currentUser);
                request.getSession().setAttribute(USER, currentUser);

                log.info("Redirect to: " + USER_CABINET_PAGE_CONTROLLER);
                response.sendRedirect(USER_CABINET_PAGE_CONTROLLER);
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
