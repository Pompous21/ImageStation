package com.yy.ImageStation.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * mp代码生成器
 * by YY
 * @since 2022-03-10
 */

public class CodeGenerator {

    public static void main(String[] argc) {
        generate();
    }

    private static void generate() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/image_station?serverTimezone=GMT%2b8", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("YY") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\_Jinan\\graduation_project\\work_space\\DIP\\ImageStation\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.yy.ImageStation") // 设置父包名
                            .moduleName(null) // 设置父包模块名  为空null
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\_Jinan\\graduation_project\\work_space\\DIP\\ImageStation\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
//                    builder.mapperBuilder().enableMapperAnnotation();     // 可加可不加
                    builder.controllerBuilder().enableHyphenStyle()     // 开启驼峰转连字符
                            .enableRestStyle();     // 开启生成@RestController控制器
                    builder.addInclude("sys_files") // 设置需要生成的表名
                            .addTablePrefix("sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }

}
