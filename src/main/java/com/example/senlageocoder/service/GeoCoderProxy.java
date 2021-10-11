package com.example.senlageocoder.service;

import com.example.senlageocoder.utils.HttpExecutor;
import com.example.senlageocoder.utils.YandexResponseParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.ServerException;

@Component
public class GeoCoderProxy {
    private static final Logger LOGGER = Logger.getLogger(GeoCoderProxy.class);
    private static final String ERROR_MSG = "Proxy server error has occurred";
    private static final String CACHE_MISS_MSG = "Request is missing in cache. Requesting Yandex Api, for: ";

    /**
     * This  method handles user geocode-request.
     * @param param user request:  address or geographic coordinates.
     * @return      there are 4 options for the return value:
     *               1)Normal answer. Coordinates or address.
     *               2)Normal answer. No result for given user request.
     *               3)Proxy server error message, causes:
     *                 Exception in {@link HttpExecutor#sendGet(HttpGet)}
     *                 Exception in {@link HttpExecutor#buildRequestToApi(String)}, for example - bad URI.
     *               4)Remote server error message (Bad apiKey, bad request and etc.)
     */
    @Cacheable(value = "requests", key = "#param")
    public String geocode(final String param) {
        LOGGER.info(CACHE_MISS_MSG + param);
        String response;
        try {
            response = HttpExecutor.sendGet(HttpExecutor.buildRequestToApi(param));
        } catch (ServerException serverException) {
            LOGGER.error(serverException.getMessage());
            return serverException.toString();
        } catch (IOException | URISyntaxException exception) {
            LOGGER.error(exception.getMessage());
            return ERROR_MSG;
        }
        JsonObject convertedResponse = new Gson().fromJson(response, JsonObject.class);
        return YandexResponseParser.parse(convertedResponse, param);
    }
}
