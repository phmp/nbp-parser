package pl.parser.nbp.xmlparsing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.parser.nbp.utils.ExchangeRates;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RatesXmlParser {

    private static final String RATES_TAG_NAME = "Rates";
    private static final String SINGLE_RATE_TAG_NAME = "Rate";
    private static final String BUYING_RATE_TAG_NAME = "Bid";
    private static final String SELLING_RATE_TAG_NAME = "Ask";
    private static final int INDEX_OF_FIRST_ELEMENT = 0;

    private Element rootElement;

    public RatesXmlParser(InputStream xml) throws IOException, SAXException, ParserConfigurationException {
        this.rootElement = getRootElement(xml);
        xml.close();
    }

    public ExchangeRates getRates(){

        NodeList ratesNodeList = getRatesFromRoot();

        List<BigDecimal> buyingRates = new ArrayList<BigDecimal>();
        List<BigDecimal> sellingRates = new ArrayList<BigDecimal>();
        for (int i =0 ; i< ratesNodeList.getLength(); i++) {
            Element element = (Element) ratesNodeList.item(i);

            BigDecimal buyingRate = getElementContentFromParentElementAsBigDecimal(element, BUYING_RATE_TAG_NAME);
            buyingRates.add(buyingRate);

            BigDecimal sellingRate = getElementContentFromParentElementAsBigDecimal(element, SELLING_RATE_TAG_NAME);
            sellingRates.add(sellingRate);
        }
        return new ExchangeRates(buyingRates, sellingRates);
    }

    private Element getRootElement(InputStream xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xml);

        Element documentElement = doc.getDocumentElement();
        return documentElement;
    }

    private NodeList getRatesFromRoot() {
        NodeList rates = rootElement.getElementsByTagName(RATES_TAG_NAME);
        Node ratesNode = rates.item(INDEX_OF_FIRST_ELEMENT);
        Element ratesElement = (Element) ratesNode;
        NodeList ratesNodeList = ratesElement.getElementsByTagName(SINGLE_RATE_TAG_NAME);
        return ratesNodeList;
    }

    private BigDecimal getElementContentFromParentElementAsBigDecimal(Element parentElement, String elementTagName){
        NodeList nodeListWithOnlyOneElement = parentElement.getElementsByTagName(elementTagName);
        Node node = nodeListWithOnlyOneElement.item(INDEX_OF_FIRST_ELEMENT);
        String textContent = node.getTextContent();
        return new BigDecimal(textContent);
    }

}
