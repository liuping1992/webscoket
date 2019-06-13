package com.example.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: energy-system
 * @description: swagger配置
 * @author: Thomas.Yang
 * @create: 2018-12-19 08:47
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        // 全局访问头
        ParameterBuilder headerPar = new ParameterBuilder();
        headerPar.name("token").description("访问令牌")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();

//        List<ResponseMessage> responseMessageList = new ArrayList<>();
//        responseMessageList.add(new ResponseMessageBuilder().code(401).message("需要授权认证").responseModel(new ModelRef("ErrorMsg")).build());
//        responseMessageList.add(new ResponseMessageBuilder().code(403).message("权限不足").responseModel(new ModelRef("ErrorMsg")).build());
//        responseMessageList.add(new ResponseMessageBuilder().code(404).message("找不到资源").responseModel(new ModelRef("ErrorMsg")).build());
//        responseMessageList.add(new ResponseMessageBuilder().code(409).message("业务逻辑异常").responseModel(new ModelRef("ErrorMsg")).build());
//        responseMessageList.add(new ResponseMessageBuilder().code(422).message("参数校验异常").responseModel(new ModelRef("ErrorMsg")).build());
//        responseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部错误").responseModel(new ModelRef("ErrorMsg")).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.websocket"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(Arrays.asList(headerPar.build()))
//                .globalResponseMessage(RequestMethod.GET, responseMessageList)
//                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger使用")
                .description("powered by lp")
                //.termsOfServiceUrl("")
                //.contact(contact)
                .version("1.0")
                .build();
    }

}
