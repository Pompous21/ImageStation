package com.yy.ImageStation.service;

import com.yy.ImageStation.controller.dto.UserDTO;
import com.yy.ImageStation.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YY
 * @since 2022-03-22
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    boolean register(UserDTO userDTO);
}
