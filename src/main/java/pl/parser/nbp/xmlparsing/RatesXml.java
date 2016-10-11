package pl.parser.nbp.xmlparsing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.parser.nbp.utils.CurrencyCode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RatesXml {

    private static final String CURRENCY_CODE_TAG_NAME = "Code";
    private static final String RATES_TAG_NAME = "Rates";
    private static final String SINGLE_RATE_TAG_NAME = "Rate";
    private static final String BUYING_RATE_TAG_NAME = "Ask";
    private static final int INDEX_OF_ONLY_ONE = 0;
    private final CurrencyCode currencyCode;
    private final List<BigDecimal> rates;
    private Element rootElement;

    public RatesXml(InputStream xml) throws ParserConfigurationException, IOException, SAXException {


        this.rootElement = getDocumentElement(xml);
        this.currencyCode = getCurrencyCodeFromRoot();
        NodeList ratesNodeList = getRatesFromRoot();

        this.rates = new ArrayList<BigDecimal>();
        for (int i =0 ; i< ratesNodeList.getLength(); i++) {
            Element element = (Element) ratesNodeList.item(i);
            String buyingRateString = getElementContentFromParentElement(element, BUYING_RATE_TAG_NAME);
            BigDecimal buyingRate = new BigDecimal(buyingRateString);
            this.rates.add(buyingRate);
        }
    }

    private NodeList getRatesFromRoot() {
        NodeList rates = rootElement.getElementsByTagName(RATES_TAG_NAME);
        Node ratesNode = rates.item(INDEX_OF_ONLY_ONE);
        Element ratesElement = (Element) ratesNode;
        NodeList ratesNodeList = ratesElement.getElementsByTagName(SINGLE_RATE_TAG_NAME);
        return ratesNodeList;
    }

    public CurrencyCode getCurrencyCode(){
        return this.currencyCode;
    }

    public List<BigDecimal> getRates(){
        return this.rates;
    }

    private Element getDocumentElement(InputStream xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);

        Element documentElement = doc.getDocumentElement();
        return documentElement;
    }

    private CurrencyCode getCurrencyCodeFromRoot(){
        String currencyCode = getElementContentFromParentElement(rootElement, CURRENCY_CODE_TAG_NAME);
        return CurrencyCode.valueOf(currencyCode);
    }

    private String getElementContentFromParentElement(Element parentElement, String elementTagName){
        NodeList nodeList = parentElement.getElementsByTagName(elementTagName);
        Node node = nodeList.item(INDEX_OF_ONLY_ONE);
        return node.getTextContent();
    }
}
