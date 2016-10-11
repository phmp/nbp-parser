package pl.parser.nbp;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import pl.parser.nbp.calculation.RatesCalculator;
import pl.parser.nbp.nbpconnection.NbpClient;
import pl.parser.nbp.utils.CurrencyCode;
import pl.parser.nbp.xmlparsing.RatesXml;

public class MainClass {

    public static void main(String[] args) {

        String output = doAllWork(args);
        System.out.print(output);

    }

    public static String doAllWork(String[] args){
        InputData inputData = new InputData(args);

        CurrencyCode currencyCode = inputData.getCurrencyCode();
        LocalDate dateFrom = inputData.getFrom();
        LocalDate dateTo = inputData.getTo();

        NbpClient nbpClient = new NbpClient();
        InputStream responseXml;

        RatesXml rates;
        try {
            responseXml = nbpClient.getRates(dateFrom, dateTo, currencyCode);
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
