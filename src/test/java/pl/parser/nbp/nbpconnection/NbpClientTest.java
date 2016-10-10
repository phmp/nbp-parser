package pl.parser.nbp.nbpconnection;

import junit.framework.Assert;
import org.testng.annotations.Test;
import pl.parser.nbp.InputData;
import pl.parser.nbp.utils.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.testng.Assert.*;


public class NbpClientTest {
    @Test
    public void testGet() throws Exception {

        NbpClient sut = new NbpClient();

        Currency currency = Currency.EUR;

        LocalDate firstDay = LocalDate.of(2016, Month.AUGUST, 1);
        LocalDate lastDay = LocalDate.of(2016, Month.AUGUST, 5);
        String rates = sut.getRates(firstDay, lastDay, currency);

        Assert.assertTrue(rates.contains("<Code>EUR</Code>"));
        Assert.assertTrue(rates.contains("<Rates>"));
        for(LocalDate day = firstDay; day.isBefore(lastDay); day = day.plusDays(1)){
            Assert.assertTrue(rates.contains(day.toString()));
        }
        System.out.println("result:" + rates);
    }

}