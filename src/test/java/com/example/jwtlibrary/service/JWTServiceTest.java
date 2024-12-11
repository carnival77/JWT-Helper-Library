package com.example.jwtlibrary.service;

import com.example.jwtlibrary.config.JWTProperties;
import com.example.jwtlibrary.exception.MissingClaimsException;
import com.example.jwtlibrary.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JWTServiceTest {

    @Autowired
    JWTProperties jwtProperties;

    JWTGenerator jwtGenerator;
    JWTService jwtService;
    Key key;

    @BeforeEach
    void setUp(){
        jwtGenerator = new JWTGenerator(jwtProperties);
        jwtService = new JWTService(jwtProperties);
        key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    // JWTGenerator의 generateToken 기반 토큰 생성 및 JWTService의 validateAndClaims 기반 검증 테스트
    @Test
    void generateAndValidateTokenTest(){
        String token = jwtGenerator.generateToken("validUser","ROLE_USER");
        Claims claims = jwtService.validateAndClaims(token);
        assertEquals(claims.getSubject(),"validUser");
    }

    // 필수 클레임 Subject가 없는 토큰 생성 및 검증 테스트 1
    @Test
    void missingSubjectClaimTest1(){
        String token = jwtGenerator.generateToken(null,"ROLE_USER");
        assertThrows(MissingClaimsException.class,()->jwtService.validateAndClaims(token));
    }

    // 필수 클레임 Subject가 없는 토큰 생성 및 검증 테스트 2
    @Test
    void missingSubjectClaimTest2(){
        String token = Jwts.builder()
                .claim("role","ROLE_USER")
                .setExpiration(new Date(System.currentTimeMillis()+1000000))
                .signWith(key,jwtProperties.getAlgorithm())
                .compact();
        assertThrows(MissingClaimsException.class,()->jwtService.validateAndClaims(token));
    }

    // 토큰 만료 예외 발생 테스트
    @Test
    void expireTokenTest() throws InterruptedException{
        String token = Jwts.builder()
                .setSubject("validUser")
                .claim("role","ROLE_USER")
                .setExpiration(new Date(System.currentTimeMillis()+1)) // 1ms 후 만료
                .signWith(key,jwtProperties.getAlgorithm())
                .compact();

        // 약간 대기해서 토큰 만료시키기
        Thread.sleep(5);

        assertThrows(TokenExpiredException.class,()->jwtService.validateAndClaims(token));
    }

    // 유효하지 않은 토큰 검증 테스트 - 토큰 불일치
    @Test
    void invalidMisMatchTokenTest(){
        String token = jwtGenerator.generateToken("validUser","ROLE_USER");
        String invalidToken = jwtGenerator.generateToken("invalidUser","ROLE_USER");
        assertNotEquals(token,invalidToken);
    }
}
