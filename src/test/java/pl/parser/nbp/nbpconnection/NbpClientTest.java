package pl.parser.nbp.nbpconnection;

import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.parser.nbp.utils.CurrencyCode;
import pl.parser.nbp.utils.exceptions.NbpConnectionException;
import pl.parser.nbp.utils.exceptions.RatesNotPublishedException;

import java.io.IOException;
import java.io.InputStream;
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

    public HttpURLConnection MOCK_CONNECTION;

    @BeforeMethod
    private void initMock() {
        MOCK_CONNECTION = Mockito.mock(HttpURLConnection.class);
    }

    class IsolatedNbpClient extends NbpClient{

        @Override
        protected HttpURLConnection getConnection(URL url) throws IOException {
            return MOCK_CONNECTION;
        }
    }

    class DissconnectedNbpClient extends NbpClient{

        @Override
        protected HttpURLConnection getConnection(URL url) throws IOException {
            throw new IOException();
        }
    }

    @Test
    public void shouldReturnResponseFromConnection() throws RatesNotPublishedException, NbpConnectionException, IOException {

        NbpClient systemUnderTest = new IsolatedNbpClient();
        InputStream expectedRates = Mockito.mock(InputStream.class);
        Mockito.when(MOCK_CONNECTION.getInputStream()).thenReturn(expectedRates);

        InputStream returnedRates = systemUnderTest.getRates(LocalDate.of(2016, Month.JANUARY, 3), LocalDate.of(2016, Month.JANUARY, 4), CurrencyCode.CHF);

        assertEquals(returnedRates, expectedRates);
    }

    @Test
    public void shouldSetRequestMethodAndPropertyConnection() throws RatesNotPublishedException, NbpConnectionException, IOException {

        NbpClient systemUnderTest = new IsolatedNbpClient();
        systemUnderTest.getRates(LocalDate.of(2016, Month.JANUARY, 3), LocalDate.of(2016, Month.JANUARY, 4), CurrencyCode.CHF);

        Mockito.verify(MOCK_CONNECTION).setRequestMethod("GET");
        Mockito.verify(MOCK_CONNECTION).setRequestProperty("Accept","application/xml");

    }

    @Test(expectedExceptions = NbpConnectionException.class, expectedExceptionsMessageRegExp = "Connection issue, check connection")
    public void whenConnectionProblemAppear_shouldThrowProperException() throws RatesNotPublishedException, NbpConnectionException, IOException {

        NbpClient systemUnderTest = new DissconnectedNbpClient();
        systemUnderTest.getRates(LocalDate.of(2016, Month.JANUARY, 3), LocalDate.of(2016, Month.JANUARY, 4), CurrencyCode.CHF);
    }

}