package com.vay.invest_bot.service.impl;

import com.vay.invest_bot.client.CbrClient;
import com.vay.invest_bot.exception.ServiceException;
import com.vay.invest_bot.service.InvestBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

@Service
@RequiredArgsConstructor
public class InvestBotServiceImpl implements InvestBotService {

    private final CbrClient client;

    private static final String USD_XPATH = "/ValCurs//Valute[@ID='R01235']/Value";
    private static final String EUR_XPATH = "/ValCurs//Valute[@ID='R01239']/Value";

    @Override
    public String getUSD() throws ServiceException {
        return null;
    }

    @Override
    public String getEUR() throws ServiceException {
        return null;
    }

    private static String extractCurrencyValueFromXML(String xml, String xpathExpration) throws ServiceException {
        var source = new InputSource(new StringReader(xml));
        try {
            var xpath = XPathFactory.newInstance().newXPath();
            var document = (Document) xpath.evaluate("/", source, XPathConstants.NODE);

            return xpath.evaluate(xpathExpration, document);
        } catch (XPathExpressionException e) {
            throw new ServiceException("Не удалось распарсить xml", e);
        }
    }
}
