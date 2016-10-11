package pl.parser.nbp;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.parser.nbp.utils.exceptions.InvalidInputException;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * positive
 * Should parse valid dates and currency and provide proper objects
 * <p>
 * TODO
 * negative
 * Should throw illegal argument exception for wrong dates order
 * Should throw illegal argument exception for unsupported currency
 * Should throw illegal argument exception for wrong date format
 * Should throw illegal argument exception for too old dates
 * Should throw illegal argument exception for wrong number of arguments
 * Should throw illegal argument exception for too long period of time between dates
 * <p>
 * manual?
 */
public class InputDataTest {

    @Test(dataProvider = "provideDates")
    public void shouldParseValidData(String currencyInput, String fromDateInput, String toDateInput) throws InvalidInputException {

        String[] args = {currencyInput, fromDateInput, toDateInput};
        InputData inputData = new InputData(args);

        final LocalDate fromDate = inputData.getFirstDay();
        Assert.assertEquals(fromDate.toString(), fromDateInput, "Should return valid fromDate");

        final LocalDate toDate = inputData.getLastDay();
        Assert.assertEquals(toDate.toString(), toDateInput, "Should return valid toDate");

        final String currency = inputData.getCurrencyCode().toString();
        Assert.assertEquals(currencyInput, currency, "Should return valid CurrencyCode");
    }

    @DataProvider
    public Object[][] provideDates() {
        return new Object[][]{
                {"USD", "2015-01-01", "2016-02-01"},
                {"EUR", "2015-01-02", "2016-01-01"},
                {"CHF", "2014-01-03", "2016-02-07"},
                {"GBP", "2011-01-01", "2011-01-01"}
        };
    }

    @Test(dataProvider = "provideInvalidData")
    public void shouldThrowInvalidInputExceptionForInvalidData(String currencyInput, String fromDateInput, String toDateInput, String expectedMessage) {
        String[] args = {currencyInput, fromDateInput, toDateInput};
        try {
            InputData inputData = new InputData(args);
            inputData.validate();
            fail("Exception should be thrown");
        }catch (InvalidInputException e){
            assertEquals(e.getMessage(), expectedMessage, "Exception with proper message should be thrown");
        }
    }

    @DataProvider
    public Object[][] provideInvalidData() {
        return new Object[][]{
                {"USD", "2015-01-02", "2015-01-01", "Dates should be provided in chronological order"},
                {"PLN", "2015-01-02", "2015-01-08", "Currency code PLN is unsupported"},
                {"ZLOTY", "2015-01-02", "2015-01-08", "Currency code ZLOTY is unsupported"},
                {"CHF", "03-01-2016", "07-02-2016", "Date should be provided in format yyyy-mm-dd"},
                {"GBP", "2002-01-01", "2002-01-02", "NBP do not provide rates published before 2002-01-02"},
                {"GBP", "2013-01-01", "2013-04-05", "Period between dates must not be longer than 93 days"},
                {"GBP", "2013-01-01", "2016-04-05", "Period between dates must not be longer than 93 days"}
        };
    }

    @Test(expectedExceptions = InvalidInputException.class, expectedExceptionsMessageRegExp = "Wrong number of arguments")
    public void constructorShouldThrowInvalidInputExceptionForWrongNumberOfArguments() throws InvalidInputException {
        String[] args = {"USD", "2015-01-02", "2015-01-01","2015-01-01"};
        new InputData(args);
    }

}