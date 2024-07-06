package com.nageoffer.shortlink.project.config;


import com.nageoffer.shortlink.project.interceptor.JwtTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    JwtTokenInterceptor jwtTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenInterceptor).addPathPatterns("/api/short-link/v1/create");
//        registry.addInterceptor(jwtTokenInterceptor)
//                .excludePathPatterns("/api/short-link/v1/page")
//                .excludePathPatterns("/api/short-link/v1/create")
//                .excludePathPatterns("/api/short-link/v1/count");
    }
}