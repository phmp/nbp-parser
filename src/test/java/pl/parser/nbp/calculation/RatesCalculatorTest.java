package pl.parser.nbp.calculation;

import org.testng.annotations.Test;
import pl.parser.nbp.utils.ExchangeRates;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;


public class RatesCalculatorTest {

    @Test
    public void shouldReturnCorrectAverage(){

        List<BigDecimal> sellingRates = Arrays.asList(new BigDecimal("2"),new BigDecimal("3"),new BigDecimal("3"),new BigDecimal("4"));
        List<BigDecimal> buyingRates = Arrays.asList(new BigDecimal("2"),new BigDecimal("3"),new BigDecimal("3"),new BigDecimal("5"));
        ExchangeRates exchangeRates = new ExchangeRates(buyingRates, sellingRates);

        RatesCalculator systemUnderTest = new RatesCalculator(exchangeRates);

        BigDecimal averageOfSellingRates = systemUnderTest.getAverageOfBuyingRates();

        assertTrue(averageOfSellingRates.compareTo( new BigDecimal("3.0000") )==0);

        BigDecimal standardDivitation = systemUnderTest.getStandardDivitationOfSellingRates();

        assertTrue(standardDivitation.compareTo(new BigDecimal("0.2500") ) == 0);


    }

}