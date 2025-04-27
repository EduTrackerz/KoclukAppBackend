package com.edutrackerz.koclukApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // tüm endpointler için
                        .allowedOrigins("http://localhost:3000")  // sadece frontend'in çalıştığı port izinli
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // izin verilen methodlar
                        .allowedHeaders("*");  // tüm header'lara izin
            }
        };
    }
}
