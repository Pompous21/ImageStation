package com.yy.ImageStation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ImageStationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageStationApplication.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "ok";
    }

}
