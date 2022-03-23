package com.yy.ImageStation.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 接收前端登录请求的参数
 */

@Data
public class UserDTO {

    private Integer id;
    private String name;
    private String phone;
    private String password;
    private String avatar;
    private String role;

    private String token;

}
