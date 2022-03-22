package com.yy.ImageStation.service.impl;

import com.yy.ImageStation.entity.User;
import com.yy.ImageStation.mapper.UserMapper;
import com.yy.ImageStation.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YY
 * @since 2022-03-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
