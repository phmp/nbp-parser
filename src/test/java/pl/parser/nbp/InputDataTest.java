package pl.parser.nbp;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.parser.nbp.utils.Currency;

import static org.testng.Assert.*;

/**
 * Created by Gosia on 09.10.2016.
 */
public class InputDataTest {

    public static final String USD = Currency.USD.toString();

    @Test(dataProvider = "provideDates")
    public void shouldParseValidDates(String from, String to){

        String [] args = {from, to, USD};
        new InputData(args);

    }

    @DataProvider
    public Object[][] provideDates(){
        return new Object[][]{
                {"2015-01-01", "2016-02-10"}
        };
    }

}