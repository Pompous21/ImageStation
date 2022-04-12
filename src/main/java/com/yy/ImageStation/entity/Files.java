package com.yy.ImageStation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author YY
 * @since 2022-03-23
 */
@Getter
@Setter
  @TableName("sys_files")
@ApiModel(value = "Files对象", description = "")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("文件在数据库中的id")
        @TableId(value = "file_id", type = IdType.AUTO)
      private Long fileId;

      @ApiModelProperty("文件名")
      private String name;

      @ApiModelProperty("文件类型")
      private String type;

      @ApiModelProperty("文件大小，以 kb 为单位")
      private Long size;

      @ApiModelProperty("文件的下载链接")
      private String url;

      @ApiModelProperty("文件是否已被删除")
      private Boolean isDeleted;

      @ApiModelProperty("文件是否被启用")
      private Boolean enable;


}
