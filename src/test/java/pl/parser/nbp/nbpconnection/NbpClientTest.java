package pl.parser.nbp.nbpconnection;

import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.testng.annotations.Test;
import pl.parser.nbp.utils.CurrencyCode;
import pl.parser.nbp.utils.exceptions.NbpConnectionException;
import pl.parser.nbp.utils.exceptions.RatesNotPublishedException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.Month;
import java.net.URL;


import static org.testng.Assert.*;

/*
*  whenConnectionThrowProtocolException_should_throwNbpConnectionExceptionWithProperMessage
*
*  shouldReturnInputStreamFromConnection
*  whenConnectionThrowException_should_throwNbpConnectionExceptionWithProperMessage
*
*
* */


public class NbpClientTest {

    public void shouldGetConnectionToCorrectAddress() throws RatesNotPublishedException, NbpConnectionException, IOException {

        HttpURLConnection mockConnection = Mockito.mock(HttpURLConnection.class);
        NbpClient systemUnderTest = Mockito.spy(new NbpClient());
        Mockito.when(systemUnderTest.getConnection(Mockito.any(URL.class))).thenReturn(mockConnection);

        systemUnderTest.getRates(LocalDate.of(2016, Month.JANUARY,3), LocalDate.of(2016, Month.JANUARY,4), CurrencyCode.CHF);

    }

}