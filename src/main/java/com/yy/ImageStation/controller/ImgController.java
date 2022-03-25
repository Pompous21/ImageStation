package com.yy.ImageStation.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.yy.ImageStation.common.Result;
import com.yy.ImageStation.mapper.impl.GetAESFunction;
import com.yy.ImageStation.service.IFilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;

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
     * @param rawImgPath
     * @return
     */
    @GetMapping("/dith")
    public String imgDith(@RequestParam String rawUrl) {
        // 暂时改成url版本
        String rawImgPath = fileUploadPath + filesService.url2fileUUID(rawUrl);

        // 处理后的图像的基本信息
        String type = filesService.fileUUID2type(rawImgPath);
        String fileUUID = filesService.initUUID(type);
        String dithedImgPath = fileUploadPath + fileUUID;

        // 处理图像
        GetAESFunction.ImgDith.INSTANCE.imgDith(rawImgPath, dithedImgPath);

        // 取出处理后的图像
        File dithedImg = new File(dithedImgPath);

        // 存入数据库
        String dithedUrl = "http://" + serverIp + ":" + serverPort + "/files/" + fileUUID;
        String md5 = SecureUtil.md5(dithedImgPath);
        filesService.saveDbFiles(dithedImg.getName(), type, dithedImg.length(), dithedUrl, md5);


        return dithedUrl;
    }

}
