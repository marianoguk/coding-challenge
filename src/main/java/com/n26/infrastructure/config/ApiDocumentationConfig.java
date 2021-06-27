package com.n26.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;

import static java.util.Collections.emptyList;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@Import({BeanValidatorPluginsConfiguration.class})
public class ApiDocumentationConfig {

    @Value("${api-documentation.title}")
    private String title;
    @Value("${api-documentation.description}")
    private String description;

    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.n26.infrastructure.resource"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                title,
                description,
                "API TOS",
                null,
                null,
                null,
                null,
                emptyList()
        );
    }
}
