package by.stepanov.hotel.service.impl.validator;

import by.stepanov.hotel.service.ServiceException;
import junit.framework.TestCase;
import org.junit.Test;

import java.time.LocalDate;

public class SuitableDateValidatorTest extends TestCase {

    @Test
    public void testCheckDates()  {
        try {
            assertEquals("Out date must be after in date", SuitableDateValidator.checkDates(
                    String.valueOf(LocalDate.now()), String.valueOf(LocalDate.now().minusDays(1))));
            assertEquals("Date must be not before today", SuitableDateValidator.checkDates(
                    String.valueOf(LocalDate.now().minusDays(1)), String.valueOf(LocalDate.now().plusDays(2))));
            assertEquals("In date and out date must not be equals", SuitableDateValidator.checkDates(
                    String.valueOf(LocalDate.now().plusDays(1)), String.valueOf(LocalDate.now().plusDays(1))));
            SuitableDateValidator.checkDates(null, null);
        } catch (ServiceException e){
            assertEquals("Date is equals null" ,e.getMessage());
        }
    }
}