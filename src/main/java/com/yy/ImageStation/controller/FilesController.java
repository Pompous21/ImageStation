package com.yy.ImageStation.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.ImageStation.common.Constants;
import com.yy.ImageStation.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.yy.ImageStation.service.IFilesService;
import com.yy.ImageStation.entity.Files;
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

    // 新增、修改
    @PostMapping
    public Result save(@RequestBody Files files) {
        if (filesService.saveOrUpdate(files)) return Result.success(Constants.CODE_5400);
        else return Result.error(Constants.CODE_5401);
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {        // 删除单个 - 根据id
        if (filesService.removeById(id)) return Result.success(Constants.CODE_5410);
        else return Result.error(Constants.CODE_5411);
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {     // 批量删除
        if (filesService.removeBatchByIds(ids)) return Result.success(Constants.CODE_5420);
        else return Result.error(Constants.CODE_5421);
    }

    // 查询
    @GetMapping
    public Result findAll() {       // 查询全部
        return Result.success(Constants.CODE_5430, filesService.list());
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                        @RequestParam Integer pageSize) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(Constants.CODE_5430, filesService.page(new Page<>(pageNum, pageSize), queryWrapper));
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
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义唯一标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUid = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUUid);

        // 定义基本信息
        String url;
        String md5;

        // 先验证父目录
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }

        // 上传到磁盘
        file.transferTo(uploadFile);

        // 操作md5去重
        md5 = SecureUtil.md5(uploadFile);
        Files dbFiles = getFileByMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
            uploadFile.delete();
        }
        else {
            url = "http://" + serverIp + ":" + serverPort + "/files/" + fileUUid;
        }

        // 存入数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        filesService.save(saveFile);

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
        // 根据UUID获取文件
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

    /**
     * 通过md5查询文件
     * @param md5
     * @return
     */
    private Files getFileByMd5(String md5) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList = filesService.list(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

}

