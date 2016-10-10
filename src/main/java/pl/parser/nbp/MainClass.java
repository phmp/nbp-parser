package pl.parser.nbp;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.List;

import org.xml.sax.SAXException;
import pl.parser.nbp.nbpconnection.NbpClient;
import pl.parser.nbp.utils.Currency;
import pl.parser.nbp.xmlparsing.RatesXml;

import javax.xml.parsers.ParserConfigurationException;

public class MainClass {

    public static void main(String[] args) {

        String output = doAllWork(args);
        System.out.print(output);

    }

    public static String doAllWork(String[] args) {
        InputData inputData = new InputData(args);

        Currency currency = inputData.getCurrency();
        LocalDate dateFrom = inputData.getFrom();
        LocalDate dateTo = inputData.getTo();

        NbpClient nbpClient = new NbpClient();
        String responseXml;
        try {
            responseXml = nbpClient.getRates(dateFrom, dateTo, currency);
            RatesXml rates = new RatesXml(responseXml);
            List<BigDecimal> ratesList = rates.getRates();

            BigDecimal sum = new BigDecimal(0);
            for (BigDecimal rate: ratesList) {
                sum = sum.add(rate);
            }
            BigDecimal n = new BigDecimal(ratesList.size());
            BigDecimal average = sum.divide(n);

            BigDecimal standardDevitation = new BigDecimal(0);
            for (BigDecimal rate: ratesList) {
                BigDecimal augent = average.subtract(rate).pow(2);
                standardDevitation = standardDevitation.add(augent);
            }
            System.out.print("" +n);
            standardDevitation = standardDevitation.divide(n);

            standardDevitation = new BigDecimal(Math.sqrt(standardDevitation.doubleValue()));

            return average.round(new MathContext(5)).toPlainString()+"\n"+standardDevitation.round(new MathContext(3)).toPlainString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

}
