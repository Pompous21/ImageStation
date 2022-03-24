package com.yy.ImageStation.controller;

import com.yy.ImageStation.mapper.impl.GetAESFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/img")
public class ImgController {
    // 图像处理

    @GetMapping("dith")
    public int imgDith() {
        String rawImgPath = "D:/TestPic/TheEarth.jpg";
        String dithedImgPath = "D:/TestPic/Space/TheEarth_Dithed.jpg";

        return GetAESFunction.ImgDith.INSTANCE.imgDith(rawImgPath, dithedImgPath);
    }

}
