package com.yy.ImageStation.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.yy.ImageStation.entity.User;
import com.yy.ImageStation.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

// 生成JWT的token
@Component
public class TokenUtils {       // 先把这个注册成Springboot的一个Bean

    // 为了后台查询当前用户信息
    private static IUserService staticUserService;

    @Resource
    private IUserService userService;       // 再引进来

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;        // 并由这个将动态对象转为静态对象
    }

    /**
     * 生成Token
     * @return
     */
    public static String genToken(String userId, String sign) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面，作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) //两小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户信息
     *
     */
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();     // 跟JWTInterceptor.java中的有点像
            String token = request.getHeader("token");

            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        }
        catch (Exception e) {
            return null;
        }

        return null;
    }
}
