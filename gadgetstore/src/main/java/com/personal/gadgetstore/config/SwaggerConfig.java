package com.personal.gadgetstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.personal.gadgetstore"))
//                .paths(PathSelectors.any())
//                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.personal.gadgetstore"))
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .paths(PathSelectors.ant("/error").negate())
                .build();
        docket.apiInfo(getApiInfo());
        return docket;
    }

    private ApiInfo getApiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Gadget Store Backend: APIs",
                "Backend project of Gadget Store application",
                "1.0.0V",
                "",
                "Utkarsh Seth",
                "",
                ""
        );
        return apiInfo;
    }
}
