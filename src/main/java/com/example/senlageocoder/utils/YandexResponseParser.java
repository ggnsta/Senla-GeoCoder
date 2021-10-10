package com.example.senlageocoder.utils;

import com.google.gson.JsonObject;

public class YandexResponseParser {

    public static String parse(final JsonObject response, final String params) {
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
            result = "Input data isn't correct";
        }
        return result;
    }
}
