package com.zhongrui.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2     //开启Swagger
public class SwaggerConfig {

    //配置了 Swagger 的Docket 的 Bean实例
    @Bean
    public Docket docket1(Environment environment) {
        return new Docket(DocumentationType.SWAGGER_2).groupName("A");
    }

    //配置多个Swagger组
    @Bean
    public Docket docket(Environment environment){

        //设置要显示的Swagger环境
        Profiles profiles = Profiles.of("dev","test");

        //通过environment.acceptsProfiles 判断是否处于在自己设定的环境
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("Yally")
                .enable(flag) //是否启动Swagger 默认true
                .select()
                //RequestHandlerSelectors，配置扫描接口的方式
                //basePackage("com.yally.swagger.controller")) 指定包
                .apis(RequestHandlerSelectors.any())
                .build();   //build模式
    }

    //配置Swagger 信息=apiInfo
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact("Yally", "https://www.baidu.com/", "2537465236@qq.com");
        return new ApiInfo(
                "Yally的SwaggerAPI文档",
                "哈哈哈Api Documentation",
                "1.0",
                "https://www.baidu.com/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());

    }

}
