package pl.parser.nbp.calculation;

import org.testng.annotations.Test;
import pl.parser.nbp.utils.ExchangeRates;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;


public class RatesCalculatorTest {

    public static final List<BigDecimal> SELLING_RATES = Arrays.asList(new BigDecimal("2.0000"), new BigDecimal("3.0000"), new BigDecimal("3.0000"), new BigDecimal("4.0000"));
    public static final List<BigDecimal> BUYING_RATES = Arrays.asList(new BigDecimal("2"), new BigDecimal("3"), new BigDecimal("3"), new BigDecimal("5"));
    public static final ExchangeRates EXCHANGE_RATES = new ExchangeRates(BUYING_RATES, SELLING_RATES);
    public static final BigDecimal EXPECTED_AVREAGE = new BigDecimal("3");
    public static final BigDecimal EXPECTED_STANDARD_DEVITATION = new BigDecimal("0.707");

    @Test
    public void shouldReturnCorrectAverage(){
        RatesCalculator systemUnderTest = new RatesCalculator(EXCHANGE_RATES);

        BigDecimal averageOfBuyingRates = systemUnderTest.getAverageOfBuyingRates();

        assertEquals(averageOfBuyingRates, EXPECTED_AVREAGE);
    }

    @Test
    public void shouldReturnCorrectStandardDevitation(){
        RatesCalculator systemUnderTest = new RatesCalculator(EXCHANGE_RATES);

        BigDecimal standardDivitation = systemUnderTest.getStandardDivitationOfSellingRates();

        assertEquals(standardDivitation, EXPECTED_STANDARD_DEVITATION);
    }

}