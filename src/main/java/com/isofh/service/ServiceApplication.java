package com.isofh.service;

import com.isofh.service.property.FileStorageProperties;
import com.isofh.service.property.ImageStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class, ImageStorageProperties.class
})
public class ServiceApplication extends SpringBootServletInitializer {

//    @PostConstruct
//    void started() {
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC+7"));
//    }


//    @Bean
//    public Docket studentAPI() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
////                .apis(RequestHandlerSelectors.basePackage("com.isofh.service.model"))
////                .apis(RequestHandlerSelectors.basePackage("com.isofh.service.controller"))
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//
//                .build()
//                .apiInfo(metaData()).useDefaultResponseMessages(false);
//
//    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);

    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Spring Boot Swagger")
                .build();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServiceApplication.class);
    }
}
