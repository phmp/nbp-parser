package pl.parser.nbp;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

import pl.parser.nbp.calculation.RatesCalculator;
import pl.parser.nbp.nbpconnection.NbpClient;
import pl.parser.nbp.utils.CurrencyCode;
import pl.parser.nbp.utils.ExchangeRates;
import pl.parser.nbp.utils.exceptions.ExpectedException;
import pl.parser.nbp.xmlparsing.RatesXmlParser;

public class MainClass {

    public static void main(String[] args) {

        String output;
        try {
            output = doAllWork(args);
        } catch (ExpectedException e) {
            output = e.getMessage();
        } catch (Exception e){
            output = "Unexpected exception: " + e.getMessage();
        }
        //TODO: logger
        System.out.print(output);

    }

    public static String doAllWork(String[] args) throws Exception {
        InputData inputData = new InputData(args);

        CurrencyCode currencyCode = inputData.getCurrencyCode();
        LocalDate dateFrom = inputData.getFirstDay();
        LocalDate dateTo = inputData.getLastDay();

        NbpClient nbpClient = new NbpClient();

        InputStream responseXml = nbpClient.getRates(dateFrom, dateTo, currencyCode);
        RatesXmlParser rates = new RatesXmlParser(responseXml);

        ExchangeRates exchangeRates = rates.getRates();
        RatesCalculator ratesCalculator = new RatesCalculator(exchangeRates);

        BigDecimal average = ratesCalculator.getAverageOfBuyingRates();
        BigDecimal standardDivitation = ratesCalculator.getStandardDivitationOfSellingRates();

        return average+"\n"+standardDivitation;
    }

}