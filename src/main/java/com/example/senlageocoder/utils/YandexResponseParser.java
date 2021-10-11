package com.example.senlageocoder.utils;
import com.google.gson.JsonObject;

public class YandexResponseParser {
    /** regular expression to сheck the user request contain alphabetic characters. */
    private static final String REGULAR_EXP = "[^a-zA-Zа-яА-Я]+";    //
    private static final String KEY_GEOCODER_META_DATA = "GeocoderMetaData";
    private static final String KEY_RESPONSE = "response";
    private static final String KEY_GEO_OBJECT_COLLECTION = "GeoObjectCollection";
    private static final String KEY_FEATURE_MEMBER = "featureMember";
    /** Yandex  API returns an array of matching addresses. The first element in the array will be the most accurate one. */
    private static final int INDEX_OF_BEST_MATCH = 0;
    private static final String KEY_GEO_OBJECT = "GeoObject";
    private static final String KEY_META_DATA_PROPERTY = "metaDataProperty";
    private static final String KEY_TEXT = "text";
    private static final String KEY_POINT = "Point";
    private static final String KEY_POS = "pos";
    private static final String BAD_REQUEST = "Input data isn't correct";

    public static String parse(final JsonObject response, final String params) {
        final String result;
        response.has(KEY_GEOCODER_META_DATA);
        if (response.toString().contains(KEY_GEOCODER_META_DATA)) {
            if (params.matches(REGULAR_EXP)) {
                result = response.getAsJsonObject(KEY_RESPONSE)
                        .getAsJsonObject(KEY_GEO_OBJECT_COLLECTION)
                        .getAsJsonArray(KEY_FEATURE_MEMBER)
                        .get(INDEX_OF_BEST_MATCH).getAsJsonObject()
                        .getAsJsonObject(KEY_GEO_OBJECT)
                        .getAsJsonObject(KEY_META_DATA_PROPERTY)
                        .getAsJsonObject(KEY_GEOCODER_META_DATA)
                        .get(KEY_TEXT).getAsString();
            } else {
                result = response.getAsJsonObject(KEY_RESPONSE)
                        .getAsJsonObject(KEY_GEO_OBJECT_COLLECTION)
                        .getAsJsonArray(KEY_FEATURE_MEMBER)
                        .get(INDEX_OF_BEST_MATCH).getAsJsonObject()
                        .getAsJsonObject(KEY_GEO_OBJECT)
                        .getAsJsonObject(KEY_POINT).get(KEY_POS).getAsString();
            }
        } else {
            result = BAD_REQUEST;
        }
        return result;
    }
}
