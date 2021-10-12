package com.example.senlageocoder.unitTests;

import com.example.senlageocoder.utils.YandexResponseParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class YandexResponseParserTest {

    private static final String ADDRESS = "Minsk, Nezavisimosti 54";
    private static final String EXPECTED_RESPONSE_FOR_ADDRESS = "53.916313 27.585861";

    private static final String COORDINATE = "37.597576 55.771899";
    private static final String EXPECTED_RESPONSE_FOR_COORDINATE = "Россия, Москва, 4-я Тверская-Ямская улица, 7";

    private static final String pathToAdressStandard = "src\\test\\java\\com\\example\\senlageocoder\\unitTests\\files\\ResponseForAddress.txt";
    private static final String pathToCoordinateStandard = "src\\test\\java\\com\\example\\senlageocoder\\unitTests\\files\\ResponseForCoordinate.txt";
    private static final String pathToEmptyStandard = "src\\test\\java\\com\\example\\senlageocoder\\unitTests\\files\\ResponseForEmpty.txt";
    private static final String BAD_REQUEST = "Input data isn't correct";

    @Test
    public void testCoordinateParse() throws IOException {
        String input  = Files.readString(Path.of(pathToCoordinateStandard));
        JsonObject convertedResponse = new Gson().fromJson(input, JsonObject.class);
        String result = YandexResponseParser.parse(convertedResponse,COORDINATE);
        Assert.assertEquals(result,EXPECTED_RESPONSE_FOR_COORDINATE);
    }

    @Test
    public void testAddressParse() throws IOException {
        String input  = Files.readString(Path.of(pathToAdressStandard));
        JsonObject convertedResponse = new Gson().fromJson(input, JsonObject.class);
        String result = YandexResponseParser.parse(convertedResponse,ADDRESS);
        Assert.assertEquals(result,EXPECTED_RESPONSE_FOR_ADDRESS);
    }

    @Test
    public void testNotFoundPointParse() throws IOException {
        String input  = Files.readString(Path.of(pathToEmptyStandard));
        JsonObject convertedResponse = new Gson().fromJson(input, JsonObject.class);
        String result = YandexResponseParser.parse(convertedResponse,COORDINATE);
        Assert.assertEquals(result,BAD_REQUEST);
    }
}
