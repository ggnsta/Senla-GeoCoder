package com.example.senlageocoder.utils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.ServerException;

@Component
public final class HttpExecutor {
    private static String apiKey;
    private static final Logger logger = Logger.getLogger(HttpExecutor.class);

    @Value("${yandex.apiKey}")
    public void setApiKey(String apiKey) {
        HttpExecutor.apiKey = apiKey;
    }

    private static String checkStatusCode(final HttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return  EntityUtils.toString(response.getEntity());
        } else {
            throw new ServerException("Bad response code: " + response.getStatusLine().getStatusCode());
        }
    }

    public static String sendGet(final HttpGet get) {
        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(get)) {
          result = checkStatusCode(response);
        } catch (IOException exception) {
            logger.error(exception);
        }
       return result;
    }

    public static HttpGet buildRequestToApi(final String address) {
        HttpGet get = null;
        try {
            URIBuilder builder = new URIBuilder("https://geocode-maps.yandex.ru/1.x/");
            builder.setParameter("apikey", apiKey)
                    .setParameter("format", "json")
                    .setParameter("geocode", address);
            get = new HttpGet(builder.build());
        } catch (URISyntaxException exception) {
            logger.error(exception);
        }
        return get;
    }
}
