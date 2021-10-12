package com.example.senlageocoder.unitTests;

import com.example.senlageocoder.utils.HttpExecutor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.ServerException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpExecutorTest {
    private static final String BAD_URI="http://www.example.com\u0081";
    private static final String BAD_API_KEY="0000";
    private static final String ADDRESS = "Minsk, Nezavisimosti 54";
    private static final String COORDINATE = "37.597576 55.771899";
    private static final String pathToAdressStandard = "src\\test\\java\\com\\example\\senlageocoder\\unitTests\\files\\ResponseForAddress.txt";
    private static final String pathToCoordinateStandard = "src\\test\\java\\com\\example\\senlageocoder\\unitTests\\files\\ResponseForCoordinate.txt";
    private static final String pathToEmptyStandard = "src\\test\\java\\com\\example\\senlageocoder\\unitTests\\files\\ResponseForEmpty.txt";

    @Value("${yandex.apiKey}")
    private String normalApiKey;
    @Value("${yandex.uri}")
    private String normalUri;
    @Autowired
    HttpExecutor httpExecutor;

    @Test
    public void testNormalAddress() throws URISyntaxException, IOException {
        String response = HttpExecutor.sendGet(HttpExecutor.buildRequestToApi(ADDRESS));
        String standard  = Files.readString(Path.of(pathToAdressStandard));
        Assert.assertEquals(response,standard);
    }
    @Test
    public void testNormalCoordinate() throws URISyntaxException, IOException {
        String response = HttpExecutor.sendGet(HttpExecutor.buildRequestToApi(COORDINATE));
        String standard  = Files.readString(Path.of(pathToCoordinateStandard));
        Assert.assertEquals(response,standard );
    }

    @Test
    public void testEmptyInput() throws URISyntaxException, IOException {
        String response = HttpExecutor.sendGet(HttpExecutor.buildRequestToApi(" "));
        String standard  = Files.readString(Path.of(pathToEmptyStandard));
        Assert.assertEquals(response,standard);
    }

    @Test (expected = ServerException.class)
    public void testBadApiKey() throws URISyntaxException, IOException {
        ReflectionTestUtils.setField(HttpExecutor.class, "apiKey",BAD_API_KEY);
        HttpExecutor.sendGet(HttpExecutor.buildRequestToApi(ADDRESS));
    }

    @Test (expected = URISyntaxException.class)
    public void testBadUri() throws URISyntaxException {
        ReflectionTestUtils.setField(HttpExecutor.class, "uri", BAD_URI);
        HttpExecutor.buildRequestToApi("Minsk");
    }

    @After
    public void restoreConstants()
    {
        ReflectionTestUtils.setField(HttpExecutor.class, "uri", normalUri);
        ReflectionTestUtils.setField(HttpExecutor.class, "apiKey",normalApiKey);
    }



}
