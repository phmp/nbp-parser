package pl.parser.nbp.nbpconnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;

import pl.parser.nbp.utils.CurrencyCode;
import pl.parser.nbp.utils.exceptions.NbpConnectionException;
import pl.parser.nbp.utils.exceptions.RatesNotPublishedException;

public class NbpClient {

    private final static String NBP_URL = "http://api.nbp.pl/api/exchangerates/rates/c/";

    public InputStream getRates(LocalDate firstDay, LocalDate lastDay, CurrencyCode currencyCode) throws NbpConnectionException, RatesNotPublishedException {
        String urlString = NBP_URL + currencyCode + "/" + firstDay + "/" + lastDay + "/";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = getConnection(url);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            return getResponse(connection);
        }catch (ProtocolException e){
            throw new NbpConnectionException("Protocol issue, cannot send GET request", e);
        }catch (IOException e){
            throw new NbpConnectionException("Connection issue, check connection", e);
        }

    }

    protected HttpURLConnection getConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    private InputStream getResponse(HttpURLConnection connection) throws RatesNotPublishedException {
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            throw new RatesNotPublishedException("Rates were not published in this days", e);
        }
    }

}
