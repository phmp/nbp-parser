package pl.parser.nbp.xmlparsing;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import pl.parser.nbp.utils.CurrencyCode;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/*
* constructorShouldBeAbleToParseXmlFromString
* shouldReturnCorrectCurrencyCode
* shouldReturnAllRates
*/


public class RatesXmlParserTest {

    @Test
    public void constructorShouldBeAbleToParseXmlFromString() throws IOException, SAXException, ParserConfigurationException {
        new RatesXmlParser(new ByteArrayInputStream(STANDARD_STRING_XML.getBytes("UTF-8")));
    }

    @Test
    public void shouldReturnCorrectRates() throws IOException, SAXException, ParserConfigurationException {
        RatesXmlParser ratesXmlParser = new RatesXmlParser(new ByteArrayInputStream(STANDARD_STRING_XML.getBytes("UTF-8")));
        List<BigDecimal> buyingRates = ratesXmlParser.getRates().getBuyingRates();

        List<BigDecimal> expectedBuingRates = Arrays.asList(new BigDecimal("3.9259"),new BigDecimal("3.9712"),new BigDecimal("3.9592"));
        Assert.assertEquals(buyingRates, expectedBuingRates);

        List<BigDecimal> sellingRates = ratesXmlParser.getRates().getSailingRates();

        List<BigDecimal> expectedSellingRates = Arrays.asList(new BigDecimal("4.0053"),new BigDecimal("4.0514"),new BigDecimal("4.0392"));
        Assert.assertEquals(sellingRates, expectedSellingRates);
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