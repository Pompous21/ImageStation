package com.yy.ImageStation.controller;

import com.yy.ImageStation.mapper.impl.GetAESFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/img")
public class ImgDithController {

    @GetMapping("/dith")
    public int importTest(@RequestParam int testNum) {
        return GetAESFunction.ImgDith.INSTANCE.importTest(testNum);
    }

}
