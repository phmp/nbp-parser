package pl.parser.nbp.nbpconnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;

import pl.parser.nbp.utils.Currency;

public class NbpClient {

    String url = "http://api.nbp.pl/api/exchangerates/rates/c/";

    public InputStream getRates(LocalDate firstDay, LocalDate lastDay, Currency currency) throws IOException {
        String url = this.url + currency + "/" + firstDay + "/" + lastDay + "/";
        try {
            HttpURLConnection con = getConnection(url);

            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/xml");

            int responseCode = con.getResponseCode();
            InputStream response = con.getInputStream();
            System.out.println(response.toString());
            return response;
        }catch (IOException e){
            throw new IOException("Connection Issue, please check connection", e);
        }
    }

    private HttpURLConnection getConnection(String url) throws IOException {
        URL obj;
        try {
            obj = new URL(url);
            return (HttpURLConnection) obj.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Wrong url, please check URL correctness");
        }
    }

}
