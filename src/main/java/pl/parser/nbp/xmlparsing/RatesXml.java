package pl.parser.nbp.xmlparsing;

import pl.parser.nbp.utils.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RatesXml {

    private String xml;

    public RatesXml(String xml) {
        this.xml = xml;
    }

    public Currency getCurrency(){
        return null;
    }

    public List<BigDecimal> getRates(){
        return null;
    }

    public BigDecimal getRate(LocalDate date){
        return null;
    }
}
