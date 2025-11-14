package com.example.app.demo.RestServiceCors;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestServiceCorsApplication implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://chonsawat:8081", "http://localhost:5173")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
    }

}
