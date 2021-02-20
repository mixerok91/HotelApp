package by.stepanov.hotel.service.impl.validator;

import java.time.LocalDate;

public class SuitableDateValidator {

    public static final String OUT_DATE_MUST_BE_AFTER_IN_DATE = "Out date must be after in date";
    public static final String DATE_MUST_BE_NOT_BEFORE_TODAY = "Date must be not before today";
    public static final String IN_DATE_AND_OUT_DATE_MUST_NOT_BE_EQUALS = "In date and out date must not be equals";

    public static String checkDates(String inDate, String outDate) {

        if (LocalDate.parse(inDate).isAfter(LocalDate.parse(outDate))) {
            return OUT_DATE_MUST_BE_AFTER_IN_DATE;
        }
        if (LocalDate.parse(inDate).isBefore(LocalDate.now()) || LocalDate.parse(outDate).isBefore(LocalDate.now())) {
            return DATE_MUST_BE_NOT_BEFORE_TODAY;
        }
        if (LocalDate.parse(inDate).isEqual(LocalDate.parse(outDate))) {
            return IN_DATE_AND_OUT_DATE_MUST_NOT_BE_EQUALS;
        }
        return null;
    }
}
