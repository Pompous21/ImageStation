package com.yy.ImageStation.common;

public interface Constants {

    // 1 基本
    String CODE_1000 = "1000";      // 返回成功的信息
    String CODE_1001 = "1001";      // 犯回错误的信息

    // 2 3 登陆
    String CODE_2000 = "2000";      // 登陆成功
    String CODE_2200 = "2200";      // 注册成功

    String CODE_3000 = "3000";      // 登陆失败
    String CODE_3001 = "3001";      // 账号或密码错误
    String CODE_3002 = "3002";      // 缺少账号或密码
    String CODE_3100 = "3100";      // 未检测到token
    String CODE_3101 = "3101";      // token-id信息验证失败
    String CODE_3102 = "3102";      // token中用户不存在
    String CODE_3103 = "3103";      // token-password信息验证失败
    String CODE_3200 = "3200";      // 注册失败
    String CODE_3201 = "3201";      // 注册失败，该账号已存在
    String CODE_3202 = "3202";      // 注册失败，缺少账号或密码

    // 4 网络操作


    // 5 后台操作
    String CODE_5000 = "5000";      // 保存成功
    String CODE_5001 = "5001";      // 保存失败
    String CODE_5100 = "5100";      // 删除成功
    String CODE_5101 = "5101";      // 删除失败
    String CODE_5300 = "5300";      // 查询成功
    String CODE_5301 = "5301";      // 查询失败
    String CODE_5400 = "5400";      // 上传文件成功
    String CODE_5401 = "5401";      // 上传文件失败，具体原因保留
    String CODE_5410 = "5410";      // 删除文件成功
    String CODE_5411 = "5411";      // 删除文件失败，具体原因保留
    String CODE_5412 = "5412";      // 删除文件失败，重复删除
    String CODE_5413 = "5413";      // 删除文件失败，文件不存在
    String CODE_5420 = "5420";      // 批量删除文件成功
    String CODE_5421 = "5421";      // 批量删除文件失败，具体原因保留
    String CODE_5430 = "5430";      // 查询文件成功
    String CODE_5431 = "5431";      // 查询文件失败，具体原因保留

    // 6 自定义异常
    String CODE_6000 = "6000";      // 自定义异常

}
