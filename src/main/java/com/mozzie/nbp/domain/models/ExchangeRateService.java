package com.mozzie.nbp.domain.models;

import java.io.StringReader;
import java.math.BigDecimal;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    @Value("${https://api.nbp.pl/api/exchangerates/tables/a/}")
    private String nbpApiUrl;

    private final RestTemplate restTemplate;

    public BigDecimal getUSDRate() {
        String xmlResponse = restTemplate.getForObject(nbpApiUrl, String.class);
        return parseUSDRateFromXml(xmlResponse);
    }

    private BigDecimal parseUSDRateFromXml(String xmlResponse) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlResponse)));

            String currencyCode;
            String rate;

            NodeList rateNodes = document.getElementsByTagName("Rate");
            for (int i = 0; i < rateNodes.getLength(); i++) {
                Node rateNode = rateNodes.item(i);
                NodeList childNodes = rateNode.getChildNodes();
                currencyCode = "";
                rate = "";
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode.getNodeName().equals("Code")) {
                        currencyCode = childNode.getTextContent();
                    } else if (childNode.getNodeName().equals("Mid")) {
                        rate = childNode.getTextContent();
                    }
                }
                if ("USD".equals(currencyCode)) {
                    return new BigDecimal(rate);
                }
            }
            throw new RuntimeException("Nie znaleziono kursu dla waluty USD.");
        } catch (Exception e) {
            throw new RuntimeException("Błąd parsowania XML: " + e.getMessage());
        }
    }
}
