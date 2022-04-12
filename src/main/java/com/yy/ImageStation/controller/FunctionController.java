package com.yy.ImageStation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.ImageStation.common.Constants;
import com.yy.ImageStation.common.Result;
import org.springframework.web.bind.annotation.*;

import com.yy.ImageStation.service.IFunctionService;
import com.yy.ImageStation.entity.Function;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YY
 * @since 2022-04-12
 */
@RestController
@RequestMapping("/function")
public class FunctionController {

    @Resource
    private IFunctionService functionService;

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(functionService.getById(id));
    }

    @GetMapping
    public Result findAll() {
        return Result.success(functionService.list());
    }

}

