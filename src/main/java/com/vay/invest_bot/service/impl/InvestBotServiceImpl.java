package com.vay.invest_bot.service.impl;

import com.vay.invest_bot.client.CbrClient;
import com.vay.invest_bot.exception.ServiceException;
import com.vay.invest_bot.service.InvestBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvestBotServiceImpl implements InvestBotService {

    private final CbrClient client;

    private static final String USD_XPATH = "/ValCurs//Valute[@ID='R01235']/Value";
    private static final String EUR_XPATH = "/ValCurs//Valute[@ID='R01239']/Value";

    @Cacheable(value = "usd", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getUSD() throws ServiceException {
        var xmlOptional = client.getCurrencyRatesXml();
        var xml = xmlOptional.orElseThrow(
                () -> new ServiceException("Не удалось получить xml")
        );
        return extractCurrencyValueFromXML(xml, USD_XPATH);
    }

    @Cacheable(value = "eur", unless = "#result == null or #result.isEmpty()")
    @Override
    public String getEUR() throws ServiceException {
        var xmlOptional = client.getCurrencyRatesXml();
        var xml = xmlOptional.orElseThrow(
                () -> new ServiceException("Не удалось получить xml")
        );
        return extractCurrencyValueFromXML(xml, EUR_XPATH);
    }

    @CacheEvict("usd")
    @Override
    public void clearUSDCache() {
        log.info("Cache \"usd\" cleared");
    }

    @CacheEvict("eur")
    @Override
    public void clearEURCache() {
        log.info("Cache \"eur\" cleared");
    }

    private static String extractCurrencyValueFromXML(String xml, String xpathExpression) throws ServiceException {
        var source = new InputSource(new StringReader(xml));
        try {
            var xpath = XPathFactory.newInstance().newXPath();
            var document = (Document) xpath.evaluate("/", source, XPathConstants.NODE);

            return xpath.evaluate(xpathExpression, document);
        } catch (XPathExpressionException e) {
            throw new ServiceException("Не удалось распарсить xml", e);
        }
    }
}
