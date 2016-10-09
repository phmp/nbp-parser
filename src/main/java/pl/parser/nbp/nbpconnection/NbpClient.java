package pl.parser.nbp.nbpconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

import pl.parser.nbp.utils.Currency;

public class NbpClient {

    public String getRates(LocalDate firstDay, LocalDate lastDay, Currency currency) throws IOException {
        String url = "http://api.nbp.pl/api/exchangerates/rates/c/"+currency+"/"+firstDay+"/"+lastDay+"/";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/xml");

        int responseCode = con.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            response.append("\n");
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();
    }

}
