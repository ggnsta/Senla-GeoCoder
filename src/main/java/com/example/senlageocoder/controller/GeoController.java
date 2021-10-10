package com.example.senlageocoder.controller;

import com.example.senlageocoder.service.GeoCoderProxy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/geocode")
@RestController
public class GeoController {

    @Autowired
    private GeoCoderProxy geoCoderProxy;
    private static final Logger logger = Logger.getLogger(GeoController.class);

    @GetMapping("{param}")
    public String getPosition(@PathVariable("param") String param) {
        logger.info("Started processing a custom request: " + param);
        return geoCoderProxy.geocode(param);
    }
}
