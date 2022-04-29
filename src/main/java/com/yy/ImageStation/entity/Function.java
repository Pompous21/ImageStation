package com.yy.ImageStation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author YY
 * @since 2022-04-12
 */
@Getter
@Setter
  @TableName("sys_function")
@ApiModel(value = "Function对象", description = "")
public class Function implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("功能id")
        private Integer id;

      @ApiModelProperty("功能名称")
      private String name;

      @ApiModelProperty("访问路径")
      private String path;

      @ApiModelProperty("功能简介")
      private String introduction;

      @ApiModelProperty("Tips标题")
      private String title;

      @ApiModelProperty("Tips正文")
      private String text;

      @ApiModelProperty("展示图片1")
      private String demoImgUrlA;

      @ApiModelProperty("展示图片2")
      private String demoImgUrlB;

      @ApiModelProperty("展示图片3")
      private String demoImgUrlC;

      @ApiModelProperty("展示图片4")
      private String demoImgUrlD;


}
