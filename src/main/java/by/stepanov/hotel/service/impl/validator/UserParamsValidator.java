package by.stepanov.hotel.service.impl.validator;

public class UserParamsValidator {

    private UserParamsValidator() {
    }

    public static boolean emailValid(String email){
        if (email.matches("^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$")) {
            return true;
        }
        return false;
    }

    public static boolean passwordValid(String password){
        //latin and russian letters and digits more than 4 symbols
        if (password.matches("^[a-zA-Zа-яА-Я0-9]{4,}$")) {
            return true;
        }
        return false;
    }

    public static boolean nameValid(String password){
        //latin and russian letters and digits more than 2 symbols
        if (password.matches("^[a-zA-Zа-яА-Я]{2,}$")) {
            return true;
        }
        return false;
    }
}
