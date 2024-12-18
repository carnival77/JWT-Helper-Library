package com.example.jwtlibrary.service;

import com.example.jwtlibrary.config.AppConfig;
import com.example.jwtlibrary.config.JWTProperties;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

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
    void 토큰생성테스트_토큰정상생성(){

        // Given
        String useCase="create-account";
        String subject = "user1";
        Map<String, Object> claims = new ConcurrentHashMap<>();
//        claims.put("role","USER");
        claims.put("name","John Doe");
        claims.put("email","www.naver.com");

        // When
        String token = jwtGenerator.generateToken(useCase,subject,claims,null);

        // then
        assertNotNull(token);
    }

    @Test
    void 토큰생성테스트_claims_누락(){
        String useCase="create-account";
        String subject = "user1";
        Map<String, Object> claims = new ConcurrentHashMap<>();
//        claims.put("role","USER");
        claims.put("name","John Doe");
//        claims.put("role","ROLE_USER");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            jwtGenerator.generateToken(useCase, subject, claims, null);
        });

        // 예외 메시지 검증
        assertEquals("필수로 설정된 claims가 누락되었습니다.: "+claims, exception.getMessage());
    }

    @Test
    void 토큰생성테스트_claims_불일치(){
        String useCase="create-account";
        String subject = "user1";
        Map<String, Object> claims = new ConcurrentHashMap<>();
        claims.put("email","www.naver.com");
        claims.put("name","John Doe");
        claims.put("role","ROLE_USER");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            jwtGenerator.generateToken(useCase, subject, claims, null);
        });

        // 예외 메시지 검증
        assertEquals("필수로 설정된 claims와 입력된 claims가 다릅니다.: "+claims, exception.getMessage());
    }

    @Test
    void 토큰생성테스트_claims_유효값_검사(){
        String useCase="create-account";
        String subject = "user1";
        Map<String, Object> claims = new ConcurrentHashMap<>();
        claims.put("email",""); // 빈 값
        claims.put("name","John Doe");
//        claims.put("role","ROLE_USER");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            jwtGenerator.generateToken(useCase, subject, claims, null);
        });

        // 예외 메시지 검증
        assertEquals("필수로 설정된 claims에 대한 유효한 값이 존재하지 않습니다.: "+claims, exception.getMessage());
    }

    @Test
    void 토큰생성테스트_추가claims_작동_검사(){
        String useCase="login";
        String subject = "user1";
        Map<String, Object> claims = new ConcurrentHashMap<>();
//        claims.put("email",""); // 빈 값
//        claims.put("name","John Doe");
        claims.put("role","ROLE_USER");

        Map<String, Object> additionalClaims = new ConcurrentHashMap<>();
        additionalClaims.put("email","www.naver.com");
        additionalClaims.put("name","John Doe");

        // When
        String token = jwtGenerator.generateToken(useCase,subject,claims,additionalClaims);

        // then
        assertNotNull(token);
    }

}
