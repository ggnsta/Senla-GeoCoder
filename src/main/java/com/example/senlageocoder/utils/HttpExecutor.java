package com.example.senlageocoder.utils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.ServerException;

@Component
public final class HttpExecutor {
    private static final String API_KEY = "02fca275-7ed2-4514-9423-9fc53c33ead4";
    private static final String URI = "https://geocode-maps.yandex.ru/1.x/";
    private static final Logger logger = Logger.getLogger(HttpExecutor.class);

    private static String checkStatusCode(final HttpResponse response) throws IOException {

        switch (response.getStatusLine().getStatusCode()) {
            case 200:
                return  EntityUtils.toString(response.getEntity());
            default:
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
        URIBuilder builder;
        HttpGet get = null;
        try {
            builder = new URIBuilder(URI);
            builder.setParameter("apikey", API_KEY)
                    .setParameter("format", "json")
                    .setParameter("geocode", address);
            get = new HttpGet(builder.build());
        } catch (URISyntaxException exception) {
            logger.error(exception);
        }
        return get;
    }
}
