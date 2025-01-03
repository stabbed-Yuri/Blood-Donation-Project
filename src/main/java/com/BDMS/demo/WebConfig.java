package com.BDMS.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final StringToHBCEntityConverter stringToHBCEntityConverter;

    public WebConfig(StringToHBCEntityConverter stringToHBCEntityConverter) {
        this.stringToHBCEntityConverter = stringToHBCEntityConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToHBCEntityConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/user-photos/**")
                .addResourceLocations("file:user-photos/");
    }
}