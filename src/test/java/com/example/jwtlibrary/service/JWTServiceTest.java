package com.example.jwtlibrary.service;

import com.example.jwtlibrary.config.AppConfig;
import com.example.jwtlibrary.config.JWTProperties;
import com.example.jwtlibrary.exception.MissingClaimsException;
import com.example.jwtlibrary.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class JWTServiceTest {

    //Spring Context 수동 초기화
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    // JWTProperties 빈 주입
    JWTProperties jwtProperties = context.getBean(JWTProperties.class);

    JWTGenerator jwtGenerator;
    JWTService jwtService;

    @BeforeEach
    void setUp(){
        jwtGenerator = new JWTGenerator(jwtProperties);
        jwtService = new JWTService(jwtProperties);
    }

    // JWTGenerator의 generateToken 기반 토큰 생성 및 JWTService의 validateAndClaims 기반 검증 테스트
    @Test
    void generateAndValidateTokenTest(){

        // Given
        String useCase="create-account";
        String subject = "user1";
        Map<String, Object> claims = new ConcurrentHashMap<>();
        claims.put("name","John Doe");
        claims.put("email","www.naver.com");

        // When
        String token = jwtGenerator.generateToken(useCase,subject,claims,null);
        Claims getClaims = jwtService.validateAndClaims(token,useCase);

        // Then
        assertEquals(getClaims.getSubject(),"user1");
    }

    // 필수 클레임 Subject가 없는 토큰 생성 및 검증 테스트
    @Test
    void missingSubjectClaimTest(){

        // Given
        String useCase="create-account";
        String subject = null; // Subject가 없는 경우
        Map<String, Object> claims = new ConcurrentHashMap<>();
        claims.put("name","John Doe");
        claims.put("email","www.naver.com");

        // When
        String token = jwtGenerator.generateToken(useCase,subject,claims,null);

        // Then
        assertThrows(MissingClaimsException.class,()->jwtService.validateAndClaims(token,useCase));
    }

    // 토큰 만료 예외 발생 테스트
    @Test
    void expireTokenTest() throws InterruptedException{

        String useCase="login";
        JWTProperties.JwtConfig config = jwtProperties.getCases().get(useCase);
        Key secretKey = Keys.hmacShaKeyFor(config.getSecretKey().getBytes(StandardCharsets.UTF_8));
        SignatureAlgorithm algorithm = SignatureAlgorithm.valueOf(config.getAlgorithm());

        String token = Jwts.builder()
                .setSubject("validUser")
                .claim("role","ROLE_USER")
                .setExpiration(new Date(System.currentTimeMillis()+1)) // 1ms 후 만료
                .signWith(algorithm,secretKey)
                .compact();

        // 약간 대기해서 토큰 만료시키기
        Thread.sleep(5);

        assertThrows(TokenExpiredException.class,()->jwtService.validateAndClaims(token,useCase));
    }

    // 유효하지 않은 토큰 검증 테스트 - 토큰 불일치
    @Test
    void invalidMisMatchTokenTest(){

        String useCase="create-account";

        String subject = "user1";
        Map<String, Object> claims = new ConcurrentHashMap<>();
//        claims.put("role","USER");
        claims.put("name","John Doe");
        claims.put("email","www.naver.com");

        String token = jwtGenerator.generateToken(useCase,subject,claims,null);

        String subject2 = "user2";

        String invalidToken = jwtGenerator.generateToken(useCase,subject2,claims,null);

        assertNotEquals(token,invalidToken);
    }
}
