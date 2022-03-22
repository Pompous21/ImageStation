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
        return new Result(Constants.CODE_5030, "成功", null);
    }

    public static Result success(Object data) {
        return new Result(Constants.CODE_1000, "成功", data);
    }

    public static Result success(String code) {
        switch (code) {
            case Constants.CODE_5000: return new Result(Constants.CODE_5000, "保存成功", null);
            case Constants.CODE_5010: return new Result(Constants.CODE_5010, "删除成功", null);
            case Constants.CODE_5030: return new Result(Constants.CODE_5030, "查询成功", null);
            default: return new Result(Constants.CODE_6000, "其他情况", null);
        }
    }

    public static Result success(String code, Object data) {
        String msg = "";
        if (code == Constants.CODE_2000) {
            msg = "登录成功";
        }
        else if (code == Constants.CODE_5030) {
            msg = "查询成功";
        }
        return new Result(code, msg, data);
    }


    // 失败
    public static Result error() {
        return new Result(Constants.CODE_1001, "错误", null);
    }

    public static Result error(String code) {
        switch (code) {
            case Constants.CODE_3000: return new Result(Constants.CODE_3000, "登陆失败", null);
            case Constants.CODE_3001: return new Result(Constants.CODE_3001, "账号或密码错误", null);
            case Constants.CODE_5001: return new Result(Constants.CODE_5001, "保存失败", null);
            case Constants.CODE_5011: return new Result(Constants.CODE_5011, "删除失败", null);
            case Constants.CODE_5031: return new Result(Constants.CODE_5031, "查询失败", null);
            default: return new Result(Constants.CODE_6000, "其他错误", null);
        }
    }

}
