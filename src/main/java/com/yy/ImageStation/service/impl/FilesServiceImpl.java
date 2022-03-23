package com.yy.ImageStation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.ImageStation.entity.Files;
import com.yy.ImageStation.mapper.FilesMapper;
import com.yy.ImageStation.service.IFilesService;
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

}
