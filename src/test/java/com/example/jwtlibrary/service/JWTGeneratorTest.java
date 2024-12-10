package com.example.jwtlibrary.service;

import com.example.jwtlibrary.config.JWTProperties;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JWTGeneratorTest {

    @Autowired
    JWTProperties jwtProperties;

    JWTGenerator jwtGenerator;

    @BeforeEach
    void setUp(){
        jwtGenerator = new JWTGenerator(jwtProperties);
    }

    // JWTGenerator의 generateToken 메서드를 테스트하는 코드
    @Test
    void generateTokenTest(){
        String token = jwtGenerator.generateToken("user1","ROLE_USER");
        assertNotNull(token);
    }

}
