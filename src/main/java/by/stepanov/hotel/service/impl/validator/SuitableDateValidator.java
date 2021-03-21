package by.stepanov.hotel.service.impl.validator;

import by.stepanov.hotel.service.ServiceException;
import org.apache.log4j.Logger;

import java.time.LocalDate;

public class SuitableDateValidator {

    private static final String OUT_DATE_MUST_BE_AFTER_IN_DATE = "Out date must be after in date";
    private static final String DATE_MUST_BE_NOT_BEFORE_TODAY = "Date must be not before today";
    private static final String IN_DATE_AND_OUT_DATE_MUST_NOT_BE_EQUALS = "In date and out date must not be equals";
    private static final String DATE_IS_EQUALS_NULL = "Date is equals null";

    private static final Logger log = Logger.getLogger(SuitableDateValidator.class);

    public static String checkDates(String inDate, String outDate) throws ServiceException {

        log.info("Check dates");

        if (inDate == null || outDate == null) {
            throw new ServiceException(DATE_IS_EQUALS_NULL);
        }
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
