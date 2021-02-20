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
import java.util.Map;

public class EditUserData implements Command {

    private static final String LOGIN_PAGE = "userController?command=login_page";
    public static final String USER = "user";
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String NEW_PASSWORD = "newPassword";
    public static final String FIRST_NAME = "firstName";
    public static final String SUR_NAME = "surName";
    public static final String ERRORS = "errors";
    public static final String EDIT_USER_DATA = "/editUserData";
    public static final String USER_CABINET_PAGE_CONTROLLER = "reservationController?command=user_cabinet_page";
    public static final String ERROR_PAGE = "error?errorMessage=Ooops, something went wrong, with registration.";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession() == null){
            response.sendRedirect(LOGIN_PAGE);
        }

        UserParamsValidator userParamsValidator = new UserParamsValidator();
        UserService userService = ServiceProvider.getInstance().getUserService();

        try {
            User currentUser = (User) request.getSession().getAttribute(USER);
            String oldPassword = request.getParameter(OLD_PASSWORD);
            String newPassword = request.getParameter(NEW_PASSWORD);
            String firstName = request.getParameter(FIRST_NAME);
            String surName = request.getParameter(SUR_NAME);

            Map<String, String> errors = userParamsValidator.validateParamsForUserEditing(currentUser, oldPassword, newPassword, firstName, surName);

            if (!errors.isEmpty()){
                request.setAttribute(ERRORS, errors);
                request.getRequestDispatcher(EDIT_USER_DATA).forward(request, response);
            } else {
                currentUser.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
                currentUser.setFirstName(firstName);
                currentUser.setSurName(surName);
                userService.updateUser(currentUser);
                request.getSession().setAttribute(USER, currentUser);
                response.sendRedirect(USER_CABINET_PAGE_CONTROLLER);
            }
        } catch (ServiceException e) {
            response.sendRedirect(ERROR_PAGE);
        }
    }
}
