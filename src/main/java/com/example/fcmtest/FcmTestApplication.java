package com.example.fcmtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FcmTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FcmTestApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){

            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOrigins("https://ichatu.ga", "https://ichatu-d9085.web.app")
                            .allowedMethods("GET", "POST", "PUT", "DELETE")
                            .allowCredentials(false)
                            .maxAge(3600);
                }
            };
        }
}
