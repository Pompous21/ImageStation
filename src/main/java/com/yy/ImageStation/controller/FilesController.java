package com.yy.ImageStation.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.ImageStation.common.Constants;
import com.yy.ImageStation.common.Result;
import com.yy.ImageStation.entity.Files;
import com.yy.ImageStation.service.IFilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YY
 * @since 2022-03-23
 */
@RestController
@RequestMapping("/files")
public class FilesController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.ip}")
    private String serverIp;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private IFilesService filesService;

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {        // 删除单个 - 根据id
        if (filesService.logicalDelete(id) > 0) return Result.success(Constants.CODE_5410);
        else return Result.error(Constants.CODE_5411);
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {     // 批量删除
        if (filesService.logicalDeleteBatch(ids) > 0) return Result.success(Constants.CODE_5420);
        else return Result.error(Constants.CODE_5421);
    }


    /**
     * 文件上传
     * @param file
     * @return url
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {

        // 获取文件的基本信息
        String originalFilename = file.getOriginalFilename();       // 文件名
        String type = FileUtil.extName(originalFilename);           // 文件类型
        long size = file.getSize();                                 // 文件大小

        String url;                                                 // 文件 url

        String fileUUID = filesService.initUUID(type);              // 文件唯一标识码

        // 指定文件保存路径
        File uploadFile = new File(fileUploadPath + fileUUID);

        // 先验证父目录
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }

        // 上传到磁盘
        file.transferTo(uploadFile);
        url = "http://" + serverIp + ":" + serverPort + "/files/" + fileUUID;


        // 存入数据库
        filesService.saveDbFiles(originalFilename, type, size, url);

        return url;

    }

    /**
     * 文件下载 http://localhost:9090/file/{fileUUID}
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 后面仍需补充权限检测、删除判断和数据库查询

        // 根据fileUUID获取文件
        File downFile = new File(fileUploadPath + fileUUID);

        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(downFile));
        os.flush();
        os.close();

    }






}

