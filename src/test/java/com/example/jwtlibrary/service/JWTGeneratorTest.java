package com.example.jwtlibrary.service;

import com.example.jwtlibrary.config.AppConfig;
import com.example.jwtlibrary.config.JWTProperties;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JWTGeneratorTest {

    //Spring Context 수동 초기화
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    // JWTProperties 빈 주입
    JWTProperties jwtProperties = context.getBean(JWTProperties.class);

    JWTGenerator jwtGenerator;

    @BeforeEach
    void setUp(){
        jwtGenerator = new JWTGenerator(jwtProperties);
    }

    // JWTGenerator의 generateToken 메서드를 테스트하는 코드
    @Test
    void generateTokenTest(){
        String subject = "user1";
        Map<String, Object> claims = new ConcurrentHashMap<>();
        claims.put("role","USER");
        claims.put("name","John Doe");
        claims.put("email","www.naver.com");

        String token = jwtGenerator.generateToken(subject,claims);
        assertNotNull(token);
    }

}
