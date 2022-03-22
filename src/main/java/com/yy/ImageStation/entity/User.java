package com.yy.ImageStation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2022-03-22
 */
@Getter
@Setter
  @TableName("sys_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("用户id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户昵称")
      private String name;

      @ApiModelProperty("手机号码")
      private String phone;

      @ApiModelProperty("邮箱")
      private String email;

      @ApiModelProperty("密码")
      private String password;

      @ApiModelProperty("创建时间")
      private LocalDateTime createTime;

      @ApiModelProperty("头像")
      private String avatar;

      @ApiModelProperty("角色身份(普通用户/会员)")
      private String role;


}
