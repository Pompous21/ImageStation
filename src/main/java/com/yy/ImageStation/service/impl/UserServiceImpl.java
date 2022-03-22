package com.yy.ImageStation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yy.ImageStation.common.Constants;
import com.yy.ImageStation.controller.dto.UserDTO;
import com.yy.ImageStation.entity.User;
import com.yy.ImageStation.exception.ServiceException;
import com.yy.ImageStation.mapper.UserMapper;
import com.yy.ImageStation.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.ImageStation.utils.TokenUtils;
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

    private static final Log LOG = Log.get();

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO, true);
        if (one != null) {
            // 获取基本信息
            BeanUtil.copyProperties(one, userDTO, true);

            // 设置 token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);

            return userDTO;
        }
        else {
            throw new ServiceException(Constants.CODE_6000, "用户名或密码错误");
        }
    }

    @Override
    public boolean register(UserDTO userDTO) {
        User one = getUserInfo(userDTO, false);
        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            save(one);
            return true;
        }
        else {
            throw new ServiceException(Constants.CODE_3201, "注册失败，该账号已存在");
        }
    }

    /**
     * 获取当前用户的详细信息
     * @param userDTO
     * @param isLogin
     * @return User: 能匹配到手机号和密码 / null: 匹配不到
     */
    private User getUserInfo(UserDTO userDTO, boolean isLogin) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", userDTO.getPhone());
        if (isLogin) {      // 应对登陆的情况
            queryWrapper.eq("password", userDTO.getPassword());
        }

        User one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_6000, "数据库存在重复账号");
        }

        return one;
    }

}
