package pl.parser.nbp;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import pl.parser.nbp.calculation.RatesCalculator;
import pl.parser.nbp.nbpconnection.NbpClient;
import pl.parser.nbp.utils.CurrencyCode;
import pl.parser.nbp.utils.ExchangeRates;
import pl.parser.nbp.utils.exceptions.InvalidInputException;
import pl.parser.nbp.utils.exceptions.NbpConnectionException;
import pl.parser.nbp.xmlparsing.RatesXmlParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.testng.Assert.*;

public class MainClassTest {

    public static final BigDecimal AVERAGE = new BigDecimal("5.0000");
    public static final BigDecimal STANDARD_DEVITATION = new BigDecimal("1.0000");
    public static final NbpConnectionException EXPECTED_EXCEPTION = new NbpConnectionException("Dummy Message", new Exception());
    private MainClass systemUnderTest;
    private InputData mockInputData;
    private NbpClient mockNbpClient;
    private RatesXmlParser mockRatesXmlParser;
    private RatesCalculator mockRatesCalculator;
    private static final String[] DUMMY_ARGS = new String[]{"USD", "2013-01-01", "2013-01-10"};

    class IsolatedMainClass extends MainClass{
        @Override
        protected InputData newInputData(String[] args) throws InvalidInputException {
            return mockInputData;
        }

        @Override
        protected NbpClient newNbpClient() {
            return mockNbpClient;
        }

        @Override
        protected RatesXmlParser newRatesXmlParser(InputStream responseXml) throws IOException, SAXException, ParserConfigurationException {
            return mockRatesXmlParser;
        }

        @Override
        protected RatesCalculator newRatesCalculator(ExchangeRates exchangeRates) {
            return mockRatesCalculator;
        }
    }

    @BeforeMethod
    public void initializeMocksAndSUT() {
        mockInputData = Mockito.mock(InputData.class);
        mockNbpClient= Mockito.mock(NbpClient.class);
        mockRatesXmlParser = Mockito.mock(RatesXmlParser.class);
        mockRatesCalculator = Mockito.mock(RatesCalculator.class);

        systemUnderTest = new IsolatedMainClass();
    }

    @Test
    public void shouldReturnAverageAndStandardDevitasionFromCalculator() throws Exception {

        Mockito.when(mockRatesCalculator.getAverageOfBuyingRates()).thenReturn(AVERAGE);
        Mockito.when(mockRatesCalculator.getStandardDivitationOfSellingRates()).thenReturn(STANDARD_DEVITATION);
        String output = systemUnderTest.getAndCalculateData(DUMMY_ARGS);

        assertEquals(output, AVERAGE+"\n"+STANDARD_DEVITATION);
    }

    @Test(expectedExceptions = NbpConnectionException.class, expectedExceptionsMessageRegExp = "Dummy Message" )
    public void whenExpectedExceptionIsThrown_shouldTheSameThrowException_WithTheSameMessage() throws Exception {
        Mockito.when(mockNbpClient.getRates(Mockito.<LocalDate>any(),Mockito.<LocalDate>any(),Mockito.<CurrencyCode>any())).thenThrow(EXPECTED_EXCEPTION);
        systemUnderTest.getAndCalculateData(DUMMY_ARGS);
    }
}