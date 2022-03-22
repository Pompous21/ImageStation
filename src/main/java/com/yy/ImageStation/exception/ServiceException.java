package com.yy.ImageStation.exception;

import com.yy.ImageStation.common.Constants;
import lombok.Getter;

/**
 * 自定义异常
 */

@Getter
public class ServiceException extends RuntimeException {
    private String code;

//    public ServiceException(String code) {
//        String msg;
//        switch (code) {
//            case Constants.CODE_3100: msg = "未检测到token"; break;
//            case Constants.CODE_3101: msg = "token-id信息验证失败"; break;
//            case Constants.CODE_3102: msg = "token中用户不存在"; break;
//            case Constants.CODE_3103: msg = "token-password信息验证失败"; break;
//            default: msg = "未知异常"; break;
//        }
//        super(msg);
//        this.code = code;
//    }

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
