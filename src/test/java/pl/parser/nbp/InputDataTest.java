package pl.parser.nbp;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.parser.nbp.utils.Currency;

import java.time.LocalDate;

/**
 * positive
 * Should parse valid dates and currency and provide proper objects
 *
 * negative
 * Should throw illegal argument exception for wrong dates order
 * Should throw illegal argument exception for unsupported currency
 * Should throw illegal argument exception for wrong date format
 * Should throw illegal argument exception for too old dates
 * Should throw illegal argument exception for wrong number of arguments
 * Should throw illegal argument exception for too long period of time between dates
 *
 */
public class InputDataTest {

    @Test(dataProvider = "provideDates")
    public void shouldParseValidDates(String currencyInput, String fromDateInput, String toDateInput) {

        String[] args = {currencyInput, fromDateInput, toDateInput};
        InputData inputData = new InputData(args);

        final LocalDate fromDate = inputData.getFrom();
        Assert.assertEquals(fromDate.toString(), fromDateInput, "Should return valid fromDate");

        final LocalDate toDate = inputData.getTo();
        Assert.assertEquals(toDate.toString(), toDateInput, "Should return valid toDate");

        final String currency = inputData.getCurrency().toString();
        Assert.assertEquals(currencyInput, currency, "Should return valid Currency");
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

}