package com.yy.ImageStation.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.ImageStation.entity.Files;
import com.yy.ImageStation.mapper.FilesMapper;
import com.yy.ImageStation.service.IFilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YY
 * @since 2022-03-23
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

    @Value("${server.ip}")
    private String serverIp;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private FilesMapper filesMapper;

    /**
     * 通过md5查询文件
     * @param md5
     * @return
     */
    @Override
    public Files getFileByMd5(String md5) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList = list(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    /**
     * 假删除单个文件
     * @param id
     * @return
     */
    @Override
    public int logicalDelete(Integer id) {

        Files files = filesMapper.selectById(id);
        if (files != null && !files.getIsDeleted()) {
            files.setIsDeleted(true);
            return filesMapper.updateById(files);
        }
//        else if (files != null && files.getIsDeleted() == true) {
//            throw new ServiceException(Constants.CODE_5412, "删除文件失败，重复删除");
//        }
//        else {
//            throw new ServiceException(Constants.CODE_5413, "删除文件失败，文件不存在");
//        }
        else {
            return 0;
        }

    }

    /**
     * 假删除多个文件
     * @param ids
     * @return
     */
    @Override
    public int logicalDeleteBatch(List<Integer> ids) {
        int totalDelete = 0;
        for (Integer id : ids) {
            if (logicalDelete(id) > 0) totalDelete++;
        }
        return totalDelete;
    }

    /**
     * 定义唯一标识码
     * @param type 文件类型
     * @return
     */
    @Override
    public String initUUID(String type) {
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        return fileUUID;
    }


    /**
     * 将 url 转成 fileUUID
     * @param url 同时参数可为 url/ filePath
     * @return
     */
    @Override
    public String url2fileUUID(String url) {
        int startIndex = url.lastIndexOf("/");
        String fileUUID = url.substring(startIndex + 1);
        return fileUUID;
    }


    /**
     * 将 fileUUID 转成 type
     * @param fileUUID  同时参数可为 url/ filePath/ fileUUID
     * @return
     */
    @Override
    public String fileUUID2type(String fileUUID) {
        int startIndex = fileUUID.lastIndexOf(".");
        String type = fileUUID.substring(startIndex + 1);
        return type;
    }

    /**
     * 使用 md5 去重
     * @param md5
     * @param fileUUID
     * @return 如果有重，就返回该文件url；如果无重，就返回null
     */
    @Override
    public String deduplicateFileByMd5(String md5, String fileUUID) {
        Files dbFiles = getFileByMd5(md5);
        String url;
        if (dbFiles != null) {
            url = dbFiles.getUrl();
        }
        else {
            url = null;
        }
        return url;
    }

    @Override
    public void saveDbFiles(String originalFilename, String type, long size, String url, String md5) {
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        save(saveFile);
    }

}
