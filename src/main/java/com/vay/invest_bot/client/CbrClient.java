package com.vay.invest_bot.client;

import com.vay.invest_bot.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CbrClient {
    private final OkHttpClient client;

    @Value("${cbr.xml.url}")
    private String url;

    public String getCurrencyRatesXml() throws ServiceException {
        var request = new Request.Builder()
                .url(url)
                .build();
        try (var response = client.newCall(request).execute()) {
            var body = response.body();
            return body == null ? null : body.string();
        } catch (IOException e) {
            throw new ServiceException("Ошибка получения курса валют", e);
        }
    }
}
