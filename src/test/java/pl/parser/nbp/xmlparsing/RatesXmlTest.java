package pl.parser.nbp.xmlparsing;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import pl.parser.nbp.utils.CurrencyCode;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/*
* constructorShouldBeAbleToParseXmlFromString
* shouldReturnCorrectCurrencyCode
* shouldReturnAllRates
*/


public class RatesXmlTest {

    @Test
    public void constructorShouldBeAbleToParseXmlFromString() throws IOException, SAXException, ParserConfigurationException {
        new RatesXml(new ByteArrayInputStream(STANDARD_STRING_XML.getBytes("UTF-8")));
    }

    @Test
    public void shouldReturnCorrectCurrencyCode() throws IOException, SAXException, ParserConfigurationException {
        RatesXml ratesXml = new RatesXml(new ByteArrayInputStream(STANDARD_STRING_XML.getBytes("UTF-8")));
        CurrencyCode currencyCode = ratesXml.getCurrencyCode();

        Assert.assertEquals(currencyCode, CurrencyCode.USD);
    }

    @Test
    public void shouldReturnCorrectRates() throws IOException, SAXException, ParserConfigurationException {
        RatesXml ratesXml = new RatesXml(new ByteArrayInputStream(STANDARD_STRING_XML.getBytes("UTF-8")));
        List<BigDecimal> rates = ratesXml.getRates();

        List<BigDecimal> expectedRates = Arrays.asList(new BigDecimal("4.0053"),new BigDecimal("4.0514"),new BigDecimal("4.0392"));

        Assert.assertEquals(rates, expectedRates);
    }


    public static final String STANDARD_STRING_XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<ExchangeRatesSeries\n" +
            "  xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
            "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "  <Table>C</Table>\n" +
            "  <CurrencyCode>dolar amerykański</CurrencyCode>\n" +
            "  <Code>USD</Code>\n" +
            "  <Rates>\n" +
            "    <Rate>\n" +
            "      <No>002/C/NBP/2016</No>\n" +
            "      <EffectiveDate>2016-01-05</EffectiveDate>\n" +
            "      <Bid>3.9259</Bid>\n" +
            "      <Ask>4.0053</Ask>\n" +
            "    </Rate>\n" +
            "    <Rate>\n" +
            "      <No>003/C/NBP/2016</No>\n" +
            "      <EffectiveDate>2016-01-07</EffectiveDate>\n" +
            "      <Bid>3.9712</Bid>\n" +
            "      <Ask>4.0514</Ask>\n" +
            "    </Rate>\n" +
            "    <Rate>\n" +
            "      <No>004/C/NBP/2016</No>\n" +
            "      <EffectiveDate>2016-01-08</EffectiveDate>\n" +
            "      <Bid>3.9592</Bid>\n" +
            "      <Ask>4.0392</Ask>\n" +
            "    </Rate>\n" +
            "  </Rates>\n" +
            "</ExchangeRatesSeries>";

}