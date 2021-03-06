package com.example.senlageocoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class SenlaGeoCoderApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SenlaGeoCoderApplication.class, args);
    }
}
