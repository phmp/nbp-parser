package pl.parser.nbp.calculation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class RatesCalculator {

    private final BigDecimal average;
    private BigDecimal n;
    private List<BigDecimal> rates;

    public RatesCalculator(List<BigDecimal> rates) {
        this.rates = rates;
        this.n = new BigDecimal(rates.size());

        BigDecimal sum = new BigDecimal(0);
        for (BigDecimal rate: rates) {
            sum = sum.add(rate);
        }

        this.average = sum.divide(n, RoundingMode.HALF_EVEN).round(new MathContext(5,RoundingMode.HALF_EVEN));
    }

    public BigDecimal getAverage(){
        return average;
    }

    public BigDecimal getStandardDivitation(){
        BigDecimal standardDevitation = new BigDecimal(0);
        for (BigDecimal rate: rates) {
            BigDecimal augent = average.subtract(rate).pow(2);
            standardDevitation = standardDevitation.add(augent);
        }
        standardDevitation = standardDevitation.divide(n, RoundingMode.HALF_EVEN);
        standardDevitation = new BigDecimal(Math.sqrt(standardDevitation.doubleValue()), new MathContext(3,RoundingMode.HALF_EVEN));
        return standardDevitation;
    }

}
