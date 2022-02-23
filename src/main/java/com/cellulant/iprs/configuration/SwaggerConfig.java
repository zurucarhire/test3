package com.cellulant.iprs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Describe and consume the structure of the API
 */
@Configuration
@EnableSwagger2
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    public static final String DEFAULT_INCLUDE_PATTERN = "/";

    @Bean
    public Docket swaggerSpringfoxDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .apiInfo(apiInfo());

        docket = docket.select()
                .apis(RequestHandlerSelectors.basePackage("com.cellulant.iprs")).build();
        return docket;
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SCB IPRS REST API")
                .description("Best thing since sliced BREAD")
                .termsOfServiceUrl("localhost")
                .version("1.0")
                .build();
    }
}
