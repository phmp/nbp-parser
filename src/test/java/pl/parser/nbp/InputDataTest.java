package pl.parser.nbp;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.parser.nbp.utils.Currency;

import java.time.LocalDate;

/**

 Should parse valid dates and currency and provide proper objects

 Should throw illegal argument exception for wrong dates order
 Should throw illegal argument exception for unsupported currency
 Should throw illegal argument exception for wrong date format
 Should throw illegal argument exception for too old dates
 Should throw illegal argument exception for wrong number of arguments

 */
public class InputDataTest {

    @Test(dataProvider = "provideDates")
    public void shouldParseValidDates(String fromDateInput, String toDateInput, String currencyInput){

        String [] args = {fromDateInput, toDateInput, currencyInput};
        InputData inputData = new InputData(args);

        final LocalDate fromDate = inputData.getFrom();
        Assert.assertEquals(fromDate.toString(), fromDateInput, "Should return valid fromDate");

        final LocalDate toDate = inputData.getTo();
        Assert.assertEquals(toDate.toString(), toDateInput, "Should return valid toDate");

        final String currency = inputData.getCurrency().toString();
        Assert.assertEquals(currencyInput, currency, "Should return valid Currency");
    }

    @DataProvider
    public Object[][] provideDates(){
        return new Object[][]{
                {"2015-01-01", "2016-02-01", "USD"},
                {"2015-01-02", "2016-01-01", "EUR"},
                {"2014-01-03", "2016-02-07", "CHF"},
                {"2011-01-01", "2016-02-06", "GBP"}
        };
    }

}