package com.example.senlageocoder.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.ServerException;

@Component
public final class HttpExecutor {
    private static String apiKey;
    private static String uri;
    private static final String URI_KEY_API_KEY = "apikey";
    private static final String URI_KEY_FORMAT = "format";
    private static final String URI_KEY_GEOCODE = "geocode";
    private static final String URI_PARAM_JSON = "json";
    private static final String URI_KEY_SCO = "sco";
    private static final String URI_PARAM_LATLONG = "latlong";
    private static final String API_ERROR_MSG = "Yandex response: ";

    @Value("${yandex.apiKey}")
    public void setApiKey(final String apiKey) {
        HttpExecutor.apiKey = apiKey;
    }
    @Value("${yandex.uri}")
    public void setUri(final String uri) {
        HttpExecutor.uri = uri;
    }

    private static String checkStatusCode(final HttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return  EntityUtils.toString(response.getEntity());
        } else {
            throw new ServerException(API_ERROR_MSG + EntityUtils.toString(response.getEntity()));
        }
    }

    public static String sendGet(final HttpGet get) throws IOException {
        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(get)) {
            result = checkStatusCode(response);
        } catch (ServerException serverException) {
            throw serverException;
        } catch (IOException exception) {
            throw exception;
        }
       return result;
    }

    /**
     * This  method is responsible for building a URI address for
     * accessing Yandex Geocode Service with the following request format:
     * {@value HttpExecutor#URI_KEY_API_KEY}  API key to access Yandex Geocode Service;
     * {@value HttpExecutor#URI_KEY_FORMAT}  Geocoder response format, xml or json;
     * {@value HttpExecutor#URI_KEY_GEOCODE}  Address or geographic coordinates of the desired object;
     * {@value HttpExecutor#URI_KEY_SCO}  Coordinate recording order;
     * @param address Address or geographic coordinates of the desired object
     * @return get Object of {@link org.apache.http.client.methods.HttpGet}, which contains the created uri
     */
    public static HttpGet buildRequestToApi(final String address) throws URISyntaxException {
        try {
            URIBuilder builder = new URIBuilder(uri)
                    .setParameter(URI_KEY_API_KEY, apiKey)
                    .setParameter(URI_KEY_FORMAT, URI_PARAM_JSON)
                    .setParameter(URI_KEY_GEOCODE, address)
                    .setParameter(URI_KEY_SCO, URI_PARAM_LATLONG);
            HttpGet get = new HttpGet(builder.build());
            return get;
        } catch (URISyntaxException exception) {
            throw exception;
        }
    }
}
