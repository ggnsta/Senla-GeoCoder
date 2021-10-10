package com.example.senlageocoder.service;
import com.example.senlageocoder.utils.HttpExecutor;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class GeoCoderProxy {
    private static final Logger logger = Logger.getLogger(GeoCoderProxy.class);

    @Cacheable(value = "requests", key = "#param")
    public String geocode(final String param) {
        logger.info("Request is missing in cache. Requesting Yandex Api, for: " + param);
        String response = HttpExecutor.sendGet(HttpExecutor.buildRequestToApi(param));
        if (!response.isEmpty()) {
            JsonObject convertedResponse = new Gson().fromJson(response, JsonObject.class);
            return parseResponse(convertedResponse, param);
        } else {
            return "Server error occurred";
        }
    }

    private static String parseResponse(final JsonObject response, final String params) {
        String result;
        if (response.toString().contains("GeocoderMetaData")) {
            if (params.matches("[^a-zA-Zа-яА-Я]+")) {
                result = response.getAsJsonObject("response")
                        .getAsJsonObject("GeoObjectCollection")
                        .getAsJsonArray("featureMember")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("GeoObject")
                        .getAsJsonObject("metaDataProperty")
                        .getAsJsonObject("GeocoderMetaData")
                        .get("text").getAsString();
            } else {
                result = response.getAsJsonObject("response")
                        .getAsJsonObject("GeoObjectCollection")
                        .getAsJsonArray("featureMember")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("GeoObject")
                        .getAsJsonObject("Point").get("pos").getAsString();
            }
        } else {
            result = "Введены неккоректные данные";
        }
        return result;
    }
}
