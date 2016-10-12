package pl.parser.nbp;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.xml.sax.SAXException;
import pl.parser.nbp.calculation.RatesCalculator;
import pl.parser.nbp.nbpconnection.NbpClient;
import pl.parser.nbp.utils.CurrencyCode;
import pl.parser.nbp.utils.ExchangeRates;
import pl.parser.nbp.utils.exceptions.ExpectedException;
import pl.parser.nbp.utils.exceptions.InvalidInputException;
import pl.parser.nbp.xmlparsing.RatesXmlParser;

import javax.xml.parsers.ParserConfigurationException;

public class MainClass {

    public static void main(String[] args) {

        MainClass main = new MainClass();
        String output;
        try {
            output = main.getAndCalculateData(args);
        } catch (ExpectedException e) {
            output = e.getMessage();
        } catch (Exception e){
            output = "Unexpected exception: " + e.getMessage();
        }
        System.out.print(output);

    }

    public String getAndCalculateData(String[] args) throws Exception {
        InputData inputData = newInputData(args);
        inputData.validate();

        CurrencyCode currencyCode = inputData.getCurrencyCode();
        LocalDate dateFrom = inputData.getFirstDay();
        LocalDate dateTo = inputData.getLastDay();

        NbpClient nbpClient = newNbpClient();
        InputStream responseXml = nbpClient.getRates(dateFrom, dateTo, currencyCode);

        RatesXmlParser rates = newRatesXmlParser(responseXml);

        ExchangeRates exchangeRates = rates.getRates();
        RatesCalculator ratesCalculator = newRatesCalculator(exchangeRates);

        BigDecimal average = ratesCalculator.getAverageOfBuyingRates();
        BigDecimal standardDivitation = ratesCalculator.getStandardDivitationOfSellingRates();

        return average+"\n"+standardDivitation;
    }

    //methods wrap new operator to get possibility to isolate class in unit tests

    protected InputData newInputData(String[] args) throws InvalidInputException {
        return new InputData(args);
    }

    protected NbpClient newNbpClient() {
        return new NbpClient();
    }

    protected RatesXmlParser newRatesXmlParser(InputStream responseXml) throws IOException, SAXException, ParserConfigurationException {
        return new RatesXmlParser(responseXml);
    }

    protected RatesCalculator newRatesCalculator(ExchangeRates exchangeRates) {
        return new RatesCalculator(exchangeRates);
    }

}