package com.example.senlageocoder.controller;

import com.example.senlageocoder.service.GeoCoderProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/geocode/")
@RestController
public class GeoController {

    private GeoCoderProxy geoCoderProxy;

    @Autowired
    public GeoController(GeoCoderProxy geoCoderProxy) {
        this.geoCoderProxy = geoCoderProxy;
    }

    @GetMapping("/{param}")
    public String getPosition(@PathVariable("param") String param) {
        return geoCoderProxy.geocode(param);
    }
}
