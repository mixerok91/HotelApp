package by.stepanov.hotel.service.impl.validator;

import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.ServiceProvider;
import by.stepanov.hotel.service.UserService;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class UserParamsValidator {

    public static final String OLD_PASSWORD_ERROR = "oldPasswordError";
    public static final String NEW_PASSWORD_VALIDATION_ERROR = "newPasswordValidationError";
    public static final String OLD_PASSWORD_INCORRECT = "Old password incorrect";
    public static final String NEW_PASSWORD_IS_NOT_VALID = "New password is not valid";
    public static final String THE_NEW_PASSWORD_MUST_BE_DIFFERENT_FROM_THE_OLD_ONE = "The new password must be different from the old one";
    public static final String FIRS_NAME_ERROR = "firsNameError";
    public static final String SUR_NAME_ERROR = "surNameError";
    public static final String NEW_FIRST_NAME_IS_NOT_VALID = "New first name is not valid";
    public static final String NEW_SURNAME_IS_NOT_VALID = "New surname is not valid";
    public static final String EMAIL_ERROR = "emailError";
    public static final String EMAIL_IS_NOT_VALID = "Email is not valid";
    public static final String USER_WITH_THAT_EMAIL_DOES_NOT_EXIST = "User with that email does not exist";
    public static final String PASSWORD_ERROR = "passwordError";
    public static final String PASSWORD_IS_NOT_VALID = "Password is not valid";
    public static final String THAT_USER_HAS_OTHER_PASSWORD = "That user has other password";
    public static final String USER_WITH_THAT_EMAIL_ALREADY_EXIST = "User with that email already exist";
    public static final String FIRST_NAME_IS_NOT_VALID = "First name is not valid";
    public static final String SURNAME_IS_NOT_VALID = "Surname is not valid";

    private static final Logger log = Logger.getLogger(UserParamsValidator.class);

    private boolean emailValid(String email){
        log.info("Email validation");
        if (email.matches("^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$")) {
            return true;
        }
        return false;
    }

    private boolean passwordValid(String password){
        //latin and russian letters and digits more than 4 symbols
        log.info("Password validation");
        if (password.matches("^[a-zA-Zа-яА-Я0-9]{4,}$")) {
            return true;
        }
        return false;
    }

    private boolean nameValid(String password){
        //latin and russian letters and digits more than 2 symbols
        log.info("Name validation");
        if (password.matches("^[a-zA-Zа-яА-Я]{2,}$")) {
            return true;
        }
        return false;
    }

    public Map<String, String> validateParamsForUserEditing (User user,
                                                                    String oldPassword,
                                                                    String newPassword,
                                                                    String firstName,
                                                                    String surName){
        log.info("User's params validation for editing");

        Map<String, String> errors = new HashMap<>();
        if (oldPassword != null) {
            if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
                errors.put(OLD_PASSWORD_ERROR, OLD_PASSWORD_INCORRECT);
            }
        }
        if (newPassword != null) {
            if (!passwordValid(newPassword)) {
                errors.put(NEW_PASSWORD_VALIDATION_ERROR, NEW_PASSWORD_IS_NOT_VALID);
            }
            if (BCrypt.checkpw(newPassword, user.getPassword())) {
                errors.put(NEW_PASSWORD_VALIDATION_ERROR, THE_NEW_PASSWORD_MUST_BE_DIFFERENT_FROM_THE_OLD_ONE);
            }
        }
        if (firstName != null) {
            if (!nameValid(firstName)) {
                errors.put(FIRS_NAME_ERROR, NEW_FIRST_NAME_IS_NOT_VALID);
            }
        }
        if (surName != null) {
            if (!nameValid(surName)) {
                errors.put(SUR_NAME_ERROR, NEW_SURNAME_IS_NOT_VALID);
            }
        }

        return errors;
    }

    public Map<String, String> validateParamsForLogin (String email, String password) throws ServiceException {

        log.info("User's params validation for logging");

        UserService userService = ServiceProvider.getUserService();


        Map<String, String> errors = new HashMap<>();
        User user = null;

        if (!emailValid(email)){
            errors.put(EMAIL_ERROR, EMAIL_IS_NOT_VALID);
        }

        try {
            user = userService.readUser(email);
        } catch (ServiceException e) {
            throw new ServiceException();
        }

        if (user == null){
            errors.put(EMAIL_ERROR, USER_WITH_THAT_EMAIL_DOES_NOT_EXIST);
        }
        if (!passwordValid(password)){
            errors.put(PASSWORD_ERROR, PASSWORD_IS_NOT_VALID);
        }

        if (user!=null && !BCrypt.checkpw(password, user.getPassword())){
            errors.put(PASSWORD_ERROR, THAT_USER_HAS_OTHER_PASSWORD);
        }
        return errors;
    }

    public Map<String, String> validateParamsForRegistration (User user) throws ServiceException {

        log.info("User's params validation for registration");

        Map<String, String> errors = new HashMap<>();
        UserService userService = ServiceProvider.getUserService();

        if (!emailValid(user.getEmail())){
            errors.put(EMAIL_ERROR, EMAIL_IS_NOT_VALID);
        }
        if (userService.readUser(user.getEmail()) != null){
            errors.put(EMAIL_ERROR, USER_WITH_THAT_EMAIL_ALREADY_EXIST);
        }
        if (!passwordValid(user.getPassword())){
            errors.put(PASSWORD_ERROR, PASSWORD_IS_NOT_VALID);
        }
        if (!nameValid(user.getFirstName())){
            errors.put(FIRS_NAME_ERROR, FIRST_NAME_IS_NOT_VALID);
        }
        if (!nameValid(user.getSurName())){
            errors.put(SUR_NAME_ERROR, SURNAME_IS_NOT_VALID);
        }

        return errors;
    }
}