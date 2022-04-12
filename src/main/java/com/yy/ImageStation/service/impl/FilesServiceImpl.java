package com.yy.ImageStation.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.ImageStation.entity.Files;
import com.yy.ImageStation.mapper.FilesMapper;
import com.yy.ImageStation.service.IFilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    @Override
    public void saveDbFiles(String originalFilename, String type, long size, String url) {
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);
        saveFile.setUrl(url);
        save(saveFile);
    }

}
