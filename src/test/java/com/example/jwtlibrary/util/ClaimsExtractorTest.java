package com.example.jwtlibrary.util;

import com.example.jwtlibrary.config.AppConfig;
import com.example.jwtlibrary.config.JWTProperties;
import com.example.jwtlibrary.service.JWTGenerator;
import com.example.jwtlibrary.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ClaimsExtractorTest {

    //Spring Context 수동 초기화
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    // JWTProperties 빈 주입
    JWTProperties jwtProperties = context.getBean(JWTProperties.class);

    JWTGenerator jwtGenerator;
    JWTService jwtService;

    Key secretKey;

    @BeforeEach
    public void setUp() {
        secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
        jwtGenerator = new JWTGenerator(jwtProperties);
        jwtService = new JWTService(jwtProperties);
    }

    @Test
    public void testExtractClaim() {

        String subject = "user1";
        Map<String, Object> claims = new ConcurrentHashMap<>();
        claims.put("role","USER");
        claims.put("name","John Doe");
        claims.put("email","www.naver.com");

        String token = jwtGenerator.generateToken(subject, claims);
        Claims validatedClaims = jwtService.validateAndClaims(token);
        assertEquals(ClaimsExtractor.getSubject(validatedClaims), "user1");
        assertEquals(ClaimsExtractor.getStringClaims(validatedClaims, "role"), "USER");
        assertNull(ClaimsExtractor.getStringClaims(validatedClaims, "invalid"));
    }
}
