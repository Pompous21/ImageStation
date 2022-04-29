package com.yy.ImageStation.controller;

import com.yy.ImageStation.common.Result;
import com.yy.ImageStation.service.IFunctionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


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

