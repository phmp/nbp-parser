package pl.parser.nbp.utils;

import java.math.BigDecimal;
import java.util.List;

public class ExchangeRates {

    private List<BigDecimal> buyingRates;
    private List<BigDecimal> sailingRates;

    public List<BigDecimal> getBuyingRates() {

        return buyingRates;
    }

    public List<BigDecimal> getSailingRates() {
        return sailingRates;
    }

    public ExchangeRates(List<BigDecimal> buyingRates, List<BigDecimal> sailingRates) {
        this.buyingRates = buyingRates;
        this.sailingRates = sailingRates;
    }
}
