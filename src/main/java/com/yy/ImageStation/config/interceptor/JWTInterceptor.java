package com.yy.ImageStation.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.yy.ImageStation.common.Constants;
import com.yy.ImageStation.entity.User;
import com.yy.ImageStation.exception.ServiceException;
import com.yy.ImageStation.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Resource
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_3100, "无token，请重新登陆");
        }
        // 获取token中的userId
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        }
        catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_3101, "token-id信息验证失败，请重新登陆");
        }
        // 根据token中的userId查询数据库
        User user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(Constants.CODE_3102, "用户不存在，请重新登陆");
        }
        // 用户密码加签验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        }
        catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_3103, "token验证失败，请重新登陆");
        }

        return true;
    }

}