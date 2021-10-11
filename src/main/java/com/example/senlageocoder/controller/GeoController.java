package com.example.senlageocoder.controller;

import com.example.senlageocoder.service.GeoCoderProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/geocode/")
@RestController
public class GeoController {

    private GeoCoderProxy geoCoderProxy;

    @Autowired
    public GeoController(final GeoCoderProxy geoCoderProxy) {
        this.geoCoderProxy = geoCoderProxy;
    }

    @GetMapping("/{param}")
    public String getPosition(@PathVariable("param") final String param) {
        return geoCoderProxy.geocode(param);
    }
}
