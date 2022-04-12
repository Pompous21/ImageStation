package com.yy.ImageStation.service;

import com.yy.ImageStation.entity.Files;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.File;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YY
 * @since 2022-03-23
 */
public interface IFilesService extends IService<Files> {

    int logicalDelete(Integer id);

    int logicalDeleteBatch(List<Integer> ids);

    String initUUID(String type);

    String url2fileUUID(String url);

    String fileUUID2type(String fileUUID);

    void saveDbFiles(String originalFilename, String type, long size, String url);


}
