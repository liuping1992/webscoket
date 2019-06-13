package com.example.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@MapperScan("com.example.websocket.*.dao")
public class WebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketApplication.class, args);
    }

//    /**
//     * 更换 json 编解码器为 fastJson
//     *
//     * @return
//     */
//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        // 1、需要先定义一个 convert 转换消息的对象;
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//
//        //2、添加fastJson 的配置信息，比如：是否要格式化返回的json数据;
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullNumberAsZero,
//                SerializerFeature.WriteNullStringAsEmpty,
//                SerializerFeature.WriteNullListAsEmpty
//        );
//
//        //3、在convert中添加配置信息.
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//
//        //4、处理中文乱码问题
//        List<MediaType> fastMediaTypes = new ArrayList<>();
//        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        fastConverter.setSupportedMediaTypes(fastMediaTypes);
//
//        return new HttpMessageConverters(fastConverter);
//
//    }

    /**
     * 用于前端传入时间的处理
     * @return
     */
    @Bean
    public ObjectMapper serializingObjectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        MyLocalDateTimeDeserializer localDateTimeDeserializer = new MyLocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        return objectMapper;

    }
}
