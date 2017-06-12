package com.pte.liquid.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;


@Configuration
@EnableSwagger
@ComponentScan("com.pte.liquid.web.controller")
public class SwaggerConfig {

    public static final String DEFAULT_INCLUDE_PATTERNS = "/liquid/.*";

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean 
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                .includePatterns(DEFAULT_INCLUDE_PATTERNS);
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "REST API to access Liquid logging data",
                "REST API gives access to ESB logging data",
                "https://github.com/rjptegelaar/liquid-os/blob/master/LICENSE",
                "tegelaarpaul@gmail.com",
                "Apache 2.0",
                "https://github.com/rjptegelaar/liquid-os/blob/master/LICENSE"
        );
        return apiInfo;
    }
}