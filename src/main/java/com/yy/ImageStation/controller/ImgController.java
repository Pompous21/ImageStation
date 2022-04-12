package com.yy.ImageStation.controller;

import com.yy.ImageStation.mapper.impl.CppFunction;
import com.yy.ImageStation.service.IFilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;

/**
 * 服务器功能库，暂时还需要手动添加
 */
@RestController
@RequestMapping("/img")
public class ImgController {

    @Resource
    private IFilesService filesService;

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.ip}")
    private String serverIp;

    @Value("${server.port}")
    private String serverPort;


    /**
     * 基于误差扩散的图像二值化算法
     * @param rawUrl
     * @return
     */
    @GetMapping("/dith")
    public String imgDith(@RequestParam String rawUrl) {
        // url版本
        String rawImgPath = fileUploadPath + filesService.url2fileUUID(rawUrl);

        // 处理后的图像的基本信息
        String dithedType = filesService.fileUUID2type(rawImgPath);
        String dithedFileUUID = filesService.initUUID(dithedType);
        String dithedImgPath = fileUploadPath + dithedFileUUID;

        // 处理图像
        CppFunction.ImgDith.INSTANCE.imgDith(rawImgPath, dithedImgPath);

        // 取出处理后的图像
        File dithedImg = new File(dithedImgPath);

        // 存入数据库
        String dithedUrl = "http://" + serverIp + ":" + serverPort + "/files/" + dithedFileUUID;
        filesService.saveDbFiles(dithedImg.getName(), dithedType, dithedImg.length(), dithedUrl);


        return dithedUrl;
    }

}
