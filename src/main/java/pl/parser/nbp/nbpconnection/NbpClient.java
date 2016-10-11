package pl.parser.nbp.nbpconnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;

import pl.parser.nbp.utils.CurrencyCode;
import pl.parser.nbp.utils.exceptions.ConnectionException;
import pl.parser.nbp.utils.exceptions.RatesUnavaibleException;

public class NbpClient {

    private final static String NBP_URL = "http://api.nbp.pl/api/exchangerates/rates/c/";

    public InputStream getRates(LocalDate firstDay, LocalDate lastDay, CurrencyCode currencyCode) throws ConnectionException, RatesUnavaibleException {
        String url = NBP_URL + currencyCode + "/" + firstDay + "/" + lastDay + "/";

        try {
            HttpURLConnection con = getConnection(url);
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/xml");
            return getResponse(con);
        }catch (ProtocolException e){
            throw new ConnectionException("Protocol issue, cannot send GET request", e);
        }catch (IOException e){
            throw new ConnectionException("Connection issue, check connection", e);
        }

    }

    protected HttpURLConnection getConnection(String url) throws IOException {
        URL obj = new URL(url);
        return (HttpURLConnection) obj.openConnection();
    }

    private InputStream getResponse(HttpURLConnection connection) throws RatesUnavaibleException {
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            throw new RatesUnavaibleException("Rates were not published in this days", e);
        }
    }

}
