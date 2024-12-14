package com.example.jwtlibrary.config;

import com.example.jwtlibrary.service.JWTGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Theo
 * @since 2024/12/14
 */
@Configuration
public class JWTGeneratorConfiguration {
    @Bean
    public JWTGenerator jwtGenerator(JWTProperties jwtProperties) {
        return new JWTGenerator(jwtProperties);
    }
}
