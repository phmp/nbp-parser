package pl.parser.nbp;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.xml.sax.SAXException;
import pl.parser.nbp.calculation.RatesCalculator;
import pl.parser.nbp.nbpconnection.NbpClient;
import pl.parser.nbp.utils.Currency;
import pl.parser.nbp.xmlparsing.RatesXml;

import javax.xml.parsers.ParserConfigurationException;

public class MainClass {

    public static void main(String[] args) {

        String output = doAllWork(args);
        System.out.print(output);

    }

    public static String doAllWork(String[] args){
        InputData inputData = new InputData(args);

        Currency currency = inputData.getCurrency();
        LocalDate dateFrom = inputData.getFrom();
        LocalDate dateTo = inputData.getTo();

        NbpClient nbpClient = new NbpClient();
        InputStream responseXml;

        RatesXml rates;
        try {
            responseXml = nbpClient.getRates(dateFrom, dateTo, currency);
            rates = new RatesXml(responseXml);
        }catch (Exception e){
            return e.getMessage();
        }

        List<BigDecimal> ratesList = rates.getRates();
        RatesCalculator ratesCalculator = new RatesCalculator(ratesList);

        BigDecimal average = ratesCalculator.getAverage();
        BigDecimal standardDivitation = ratesCalculator.getStandardDivitation();

        return average+"\n"+standardDivitation;
    }

}
