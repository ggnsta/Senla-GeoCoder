package com.example.senlageocoder.unitTests;

import com.example.senlageocoder.controller.GeoController;
import com.example.senlageocoder.service.GeoCoderProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GeoController.class)
public class GeoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GeoCoderProxy geoCoderProxy;
    @Test
    public void testApiEndPointWithoutParameters() throws Exception {
        this.mockMvc.perform(get("/api/geocode/")).andExpect(status().is4xxClientError());
    }

    @Test
    public void testApiEndPoint() throws Exception {
        this.mockMvc.perform(get("/api/geocode/Minsk, kiseleva 29")).andExpect(status().isOk());
    }
}
