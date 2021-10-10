package com.example.senlageocoder.service;
import com.example.senlageocoder.utils.HttpExecutor;
import com.example.senlageocoder.utils.YandexResponseParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class GeoCoderProxy {
    private static final Logger logger = Logger.getLogger(GeoCoderProxy.class);

    @Cacheable(value = "requests", key = "#param")
    public String geocode(final String param) {
        logger.info("Request is missing in cache. Requesting Yandex Api, for: " + param);
        String response = HttpExecutor.sendGet(HttpExecutor.buildRequestToApi(param));
        if (!response.isEmpty()) {
            JsonObject convertedResponse = new Gson().fromJson(response, JsonObject.class);
            return YandexResponseParser.parse(convertedResponse, param);
        } else {
            return "Server error occurred";
        }
    }
}
