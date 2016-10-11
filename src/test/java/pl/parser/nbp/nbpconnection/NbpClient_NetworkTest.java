package pl.parser.nbp.nbpconnection;

import junit.framework.Assert;
import org.testng.annotations.Test;
import pl.parser.nbp.utils.CurrencyCode;
import pl.parser.nbp.utils.exceptions.NbpConnectionException;
import pl.parser.nbp.utils.exceptions.RatesNotPublishedException;

import java.io.InputStream;

import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;


public class NbpClient_NetworkTest {
    @Test
    public void shouldReturnResponseWithCurrencyAndRates() throws Exception {

        NbpClient sut = new NbpClient();

        CurrencyCode currencyCode = CurrencyCode.EUR;

        LocalDate firstDay = LocalDate.of(2016, Month.AUGUST, 1);
        LocalDate lastDay = LocalDate.of(2016, Month.AUGUST, 5);
        InputStream rates = sut.getRates(firstDay, lastDay, currencyCode);

        String responseString = toStringFromInputStream(rates);

        Assert.assertTrue(responseString.contains("<Code>EUR</Code>"));
        Assert.assertTrue(responseString.contains("<Rates>"));
        for(LocalDate day = firstDay; day.isBefore(lastDay); day = day.plusDays(1)){
            Assert.assertTrue(responseString.contains(day.toString()));
        }
        System.out.println("result:" + rates);
    }

    @Test(expectedExceptions = RatesNotPublishedException.class, expectedExceptionsMessageRegExp = "Rates were not published in this days")
    public void shouldThrowExceptionForHolidays() throws NbpConnectionException, RatesNotPublishedException {

        NbpClient sut = new NbpClient();
        CurrencyCode currencyCode = CurrencyCode.EUR;

        LocalDate firstDay = LocalDate.of(2016, Month.JANUARY, 1);
        LocalDate lastDay = LocalDate.of(2016, Month.JANUARY, 2);
        sut.getRates(firstDay, lastDay, currencyCode);
    }

    String toStringFromInputStream(InputStream inputStream){
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}