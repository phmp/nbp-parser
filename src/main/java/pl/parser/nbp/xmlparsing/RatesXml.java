package pl.parser.nbp.xmlparsing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pl.parser.nbp.utils.Currency;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RatesXml {

    private final Currency currency;
    private final List<BigDecimal> rates;
    private String xml;

    public RatesXml(String xml) throws ParserConfigurationException, IOException, SAXException {
        this.xml = xml;
        rates = new ArrayList<BigDecimal>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new StringReader(xml)));
        Element documentElement = doc.getDocumentElement();
        documentElement.normalize();
        NodeList code = documentElement.getElementsByTagName("Code");
        Node node = code.item(0);
        String nodeValue = node.getTextContent();
        this.currency = Currency.valueOf(nodeValue);
        NodeList rates = documentElement.getElementsByTagName("Rates");
        Node ratesNode = rates.item(0);
        Element ratesElement = (Element) ratesNode;
        NodeList nodeList = ratesElement.getElementsByTagName("Rate");
        for (int i =0 ; i< nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            Element element = (Element) item;
            Node ask = element.getElementsByTagName("Ask").item(0);
            String textContent = ask.getTextContent();
            BigDecimal rate = new BigDecimal(textContent);
            this.rates.add(rate);
        }
    }

    public Currency getCurrency(){
        return this.currency;
    }

    public List<BigDecimal> getRates(){
        return this.rates;
    }
}
