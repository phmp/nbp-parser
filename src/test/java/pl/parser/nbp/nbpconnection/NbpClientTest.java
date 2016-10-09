package pl.parser.nbp.nbpconnection;

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
        LocalDate lastDay = LocalDate.of(2016, Month.AUGUST, 10);
        String rates = sut.getRates(firstDay, lastDay, currency);

        System.out.println("result:" + rates);

    }

}