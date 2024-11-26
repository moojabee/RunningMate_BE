package com.lswr.demo.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lswr.demo.interceptor.JwtInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer{

    private final JwtInterceptor jwtInterceptor;

    public WebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/userAuth/**"); 
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        		.allowedOrigins("https://dynamic-pixie-884077.netlify.app","http://localhost:5173","https:runningmate.store")  // 클라이언트의 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "*")
                .exposedHeaders("Authorization")
                .allowCredentials(true);  // 인증 정보 허용
    }
}
