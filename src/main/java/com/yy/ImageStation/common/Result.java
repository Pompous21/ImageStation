package com.yy.ImageStation.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;


    // 成功

    public static Result success() {
        return new Result(Constants.CODE_5300, "成功", null);
    }

    public static Result success(Object data) {
        return new Result(Constants.CODE_1000, "成功", data);
    }

    public static Result success(String code) {
        switch (code) {
            case Constants.CODE_5000: return new Result(code, "保存成功", null);
            case Constants.CODE_5100: return new Result(code, "删除成功", null);
            case Constants.CODE_5300: return new Result(code, "查询成功", null);
            case Constants.CODE_5400: return new Result(code, "文件更新成功", null);
            case Constants.CODE_5410: return new Result(code, "文件删除成功", null);
            case Constants.CODE_5420: return new Result(code, "文件批量删除成功", null);
            default: return new Result(Constants.CODE_6000, "其他情况", null);
        }
    }

    public static Result success(String code, Object data) {
        String msg = "";
        switch (code) {
            case Constants.CODE_2000: msg = "登录成功"; break;
            case Constants.CODE_2200: msg = "注册成功"; break;
            case Constants.CODE_5300: msg = "查询成功"; break;
            case Constants.CODE_5430: msg = "文件获取成功"; break;
            default: break;
        }
        return new Result(code, msg, data);
    }


    // 失败
    public static Result error() {
        return new Result(Constants.CODE_1001, "错误", null);
    }

    public static Result error(String code) {
        switch (code) {
            case Constants.CODE_3000: return new Result(code, "登陆失败", null);
            case Constants.CODE_3001: return new Result(code, "账号或密码错误", null);
            case Constants.CODE_3002: return new Result(code, "缺少账号或密码", null);
            case Constants.CODE_3200: return new Result(code, "注册失败", null);
            case Constants.CODE_3201: return new Result(code, "注册失败，该账号已存在", null);
            case Constants.CODE_5001: return new Result(code, "保存失败", null);
            case Constants.CODE_5101: return new Result(code, "删除失败", null);
            case Constants.CODE_5301: return new Result(code, "查询失败", null);
            case Constants.CODE_5401: return new Result(code, "文件更新失败", null);
            case Constants.CODE_5411: return new Result(code, "文件删除失败", null);
            case Constants.CODE_5421: return new Result(code, "文件批量删除失败", null);
            default: return new Result(Constants.CODE_6000, "其他错误", null);
        }
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

}
