package com.yy.ImageStation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.ImageStation.common.Constants;
import com.yy.ImageStation.common.Result;
import org.springframework.web.bind.annotation.*;

import com.yy.ImageStation.service.IUserService;
import com.yy.ImageStation.entity.User;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YY
 * @since 2022-03-22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    // 新增、修改
    @PostMapping
    public Result save(@RequestBody User user) {
        if (userService.saveOrUpdate(user)) return Result.success(Constants.CODE_5000);
        else return Result.error(Constants.CODE_5001);
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {        // 删除单个 - 根据id
        if (userService.removeById(id)) return Result.success(Constants.CODE_5010);
        else return Result.error(Constants.CODE_5011);
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {     // 批量删除
        if (userService.removeBatchByIds(ids)) return Result.success(Constants.CODE_5010);
        else return Result.error(Constants.CODE_5011);
    }

    // 查询
    @GetMapping
    public Result findAll() {       // 查询全部
        return Result.success(userService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {       // 查询单个 - 根据id
        return Result.success(userService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                        @RequestParam Integer pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }
    }

