package pl.parser.nbp.xmlparsing;

import org.testng.annotations.Test;

/*
* constructorShouldBeAbleToParseXmlFromString
* shouldReturnCorrectCurrencyCode
* shouldReturnAllRates
*/


public class RatesXmlTest {

    @Test
    public void constructorShouldBeAbleToParseXmlFromString(){
        new RatesXml(STANDARD_STRING_XML);
    }



    public static final String STANDARD_STRING_XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<ExchangeRatesSeries\n" +
            "  xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
            "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "  <Table>C</Table>\n" +
            "  <Currency>dolar amerykański</Currency>\n" +
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