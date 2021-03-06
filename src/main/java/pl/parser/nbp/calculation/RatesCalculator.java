package pl.parser.nbp.calculation;

import pl.parser.nbp.utils.ExchangeRates;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class RatesCalculator {

    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    private final List<BigDecimal> buyingRates;
    private final List<BigDecimal> sellingRates;

    public RatesCalculator(ExchangeRates exchangeRates) {
        this.buyingRates = exchangeRates.getBuyingRates();
        this.sellingRates = exchangeRates.getSailingRates();
    }

    public BigDecimal getAverageOfBuyingRates(){
        return getAverage(buyingRates);
    }

    private BigDecimal getAverage(List<BigDecimal> rates){
        BigDecimal numberOfRates = new BigDecimal(rates.size());

        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal rate: rates) {
            sum = sum.add(rate);
        }

        return sum.divide(numberOfRates, RoundingMode.HALF_EVEN).round(new MathContext(5, ROUNDING_MODE));
    }

    public BigDecimal getStandardDivitationOfSellingRates(){
        BigDecimal standardDevitation = BigDecimal.ZERO;
        BigDecimal average = getAverage(sellingRates);
        BigDecimal numberOfRates = new BigDecimal(sellingRates.size());

        for (BigDecimal rate: sellingRates) {
            BigDecimal augent = average.subtract(rate).pow(2);
            standardDevitation = standardDevitation.add(augent);
        }

        standardDevitation = standardDevitation.divide(numberOfRates, ROUNDING_MODE);

        standardDevitation = calculateSqrt(standardDevitation);

        return standardDevitation;
    }

    private BigDecimal calculateSqrt(BigDecimal x){
        double sqrt = Math.sqrt(x.doubleValue());
        return new BigDecimal(sqrt, new MathContext(3, ROUNDING_MODE));
    }

}
