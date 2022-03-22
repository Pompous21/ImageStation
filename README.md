# 创建工程

Developer Tools - Lombok

Web - Spring Web(SpringMVC)

SQL - MyBatis Framework
      MySQL Driver

Spring Boot 2.5.10


## 更改配置

Settings - Build, Execution, Development - Build Tools - Maven - Maven home path - maven-3.8.4


## 去除冗余

- `.mvn`
- `mvnw`
- `mvnw.cmd`


## 基本配置
配置数据库
``` yml
# In application.yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/image_station?serverTimezone=GMT%2b8
    username: root
    password: 123456
```

配置路由和端口
``` yml
# In application.yml
Server:
  ip: localhost
  port: 9090
```

配置跨域（前端不处理跨域，只在后端处理）
``` java
// In CorsConfig.java
package com.yy.img_processing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    // 当前跨域请求最大有效时长。这里默认1天
    private static final long MAX_AGE = 24 * 60 * 60;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1 设置访问源地址
        corsConfiguration.addAllowedHeader("*"); // 2 设置访问源请求头
        corsConfiguration.addAllowedMethod("*"); // 3 设置访问源请求方法
        corsConfiguration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**", corsConfiguration); // 4 对接口配置跨域设置
        return new CorsFilter(source);
    }
}

```


## 测试配置
``` java
@RestController


    @GetMapping("/")
    public String index() {
        return "ok";
    }
```


# 开始使用

## 使用 Mybatis

``` yml
# In application.yml
mybatis:
  mapper-locations: classpath:mapper/*.xml
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

逐层调用
- `.eneity/` 存放实体类
  - JavaSE - Getter and Setter
  - Lombok - @Data / @NoArgsConstructor / @AllArgsConstructor 后两者可选
- `.mapper/` 数据库查询的接口(interface)
  - ibatis - @Mapper 识别成是Mapper，后面使用MBP就改位置了
  - Mybatis - @Select / @Delete / @Insert / @Update
  - javax - @Resource 注入
- `.service/`
  - spring - @Service
- `.controller/`
  - spring - @RestController 

同时，需要使用动态 SQL 语句的时候，在/resources/mapper/UserMapper.xml等写，具体写法参考示例项目，后续使用MBP会将其淘汰，此处不再赘述。


## 安装和使用 Mybatis-Plus
``` xml
<!--  mybatis-plus  -->
    <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus-boot-starter</artifactId>
      <version>3.5.1</version>
    </dependency>
```

``` java
// In MybatisPlusConfig.java
@Configuration
@MapperScan("...mapper")

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
```

``` yml 
# In application.yml
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```


## 安装和使用 Swagger
``` xml
    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-boot-starter</artifactId>
      <version>3.0.0</version>
    </dependency>
```

``` java
// In SwaggerConfig.java
package com.yy.img_processing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Value("${config.swagger3.flag}")
    private boolean flag;

    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("标准接口")
                .apiInfo(apiInfo("Spring Boot中使用Swagger3构建RESTful APIs", "1.0"))
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("...controller"))
                .paths(PathSelectors.any())
                .build()
                .enable(flag);
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://ip:port/swagger-ui/index.html
     *
     * @return
     */
    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfoBuilder()
                .title(title)
                .description("描述的内容")
            //     .termsOfServiceUrl("url")
            //     .contact(new Contact("name", "url", "email"))
                .version(version)
                .build();
    }
}
```

``` yml
# In application.yml
config:
  swagger3:
    flag: true
```


## 安装和使用 Mybatis-Plus Generator
``` xml
    <!-- mybatis-plus代码生成器 -->
    <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus-generator</artifactId>
      <version>3.5.1</version>
    </dependency>
    <!-- 生成器的依赖 -->
    <dependency>
      <groupId>org.apache.velocity</groupId>
      <artifactId>velocity</artifactId>
      <version>1.7</version>
    </dependency>
```

``` java
// In CodeGenerator.java
package com.yy.img_processing.utils;

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
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/processing?serverTimezone=GMT%2b8", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("YY") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\_Jinan\\graduation_project\\work_space\\b01_imgProcessing\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.yy.img_processing") // 设置父包名
                            .moduleName(null) // 设置父包模块名  为空null
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\_Jinan\\graduation_project\\work_space\\b01_imgProcessing\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
//                    builder.mapperBuilder().enableMapperAnnotation();     // 可加可不加
                    builder.controllerBuilder().enableHyphenStyle()     // 开启驼峰转连字符
                                                .enableRestStyle();     // 开启生成@RestController控制器
                    builder.addInclude("sys_menu") // 设置需要生成的表名
                            .addTablePrefix("sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }

}

```

此外，要自定义修改生成的模板，可在 `外部库\Maven:com.baomidou:mybatis-plus-generator-3.5.1\mybatis-plus-generator-3.5.1.jar!\templates` 复制一份到 `src/main/resources/templates` 后修改


# 安装 hutool

``` xml
<!--hutool-->
    <dependency>
      <groupId>cn.hutool</groupId>
      <artifactId>hutool-all</artifactId>
      <version>5.7.22</version>
    </dependency>
```

## 安装和使用 JWT

参考链接附上

[SpringBoot集成JWT实现token验证](https://blog.csdn.net/gjtao1130/article/details/111658060?spm=1001.2014.3001.5502)

``` xml
		<dependency>
			<groupId>com.auth0</groupId>
			<artifactId>java-jwt</artifactId>
			<version>3.10.3</version>
		</dependency>
```

需要逐项完成：

生成token / 获取用户信息
``` java
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
```

拦截器拦截token
``` java
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
            throw new ServiceException(Constants.CODE_6000, "无token，请重新登陆");
        }
        // 获取token中的userId
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        }
        catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_6000, "token-id信息验证失败，请重新登陆");
        }
        // 根据token中的userId查询数据库
        User user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(Constants.CODE_6000, "用户不存在，请重新登陆");
        }
        // 用户密码加签验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        }
        catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_6000, "token验证失败，请重新登陆");
        }

        return true;
    }

}
```

注册拦截器
``` java
package com.yy.ImageStation.config;

import com.yy.ImageStation.config.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")    // 拦截所有请求，通过判断token是否合法决定是否需要登录，但要排除登录注册导入导出接口
                .excludePathPatterns("/user/login", "user/register",
                        "/swagger-resources", "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/api", "/api-docs", "/api-docs/**");
    }

    @Bean
    public JWTInterceptor jwtInterceptor() {
        return new JWTInterceptor();
    }
}
```

