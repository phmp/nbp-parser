package pl.parser.nbp.nbpconnection;

import junit.framework.Assert;
import org.testng.annotations.Test;
import pl.parser.nbp.utils.Currency;

import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;


public class NbpClientTest {
    @Test
    public void testGet() throws Exception {

        NbpClient sut = new NbpClient();

        Currency currency = Currency.EUR;

        LocalDate firstDay = LocalDate.of(2016, Month.AUGUST, 1);
        LocalDate lastDay = LocalDate.of(2016, Month.AUGUST, 5);
        InputStream rates = sut.getRates(firstDay, lastDay, currency);

        String responseString = toStringFromInputStream(rates);

        Assert.assertTrue(responseString.contains("<Code>EUR</Code>"));
        Assert.assertTrue(responseString.contains("<Rates>"));
        for(LocalDate day = firstDay; day.isBefore(lastDay); day = day.plusDays(1)){
            Assert.assertTrue(responseString.contains(day.toString()));
        }
        System.out.println("result:" + rates);
    }

    String toStringFromInputStream(InputStream inputStream){
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}