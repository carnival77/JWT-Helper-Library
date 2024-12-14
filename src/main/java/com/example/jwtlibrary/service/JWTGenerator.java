package com.example.jwtlibrary.service;

import com.example.jwtlibrary.config.JWTProperties;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTGenerator {

    private final Key secretKey; // Secret Key를 바탕으로 HMAC 서명키를 생성한다.
    private final long expiration; // 만료 시간을 바탕으로 토큰의 exp Claim 설정.
    private final SignatureAlgorithm algorithm; // 해시 암호화 알고리즘

    public JWTGenerator(JWTProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
        this.expiration = jwtProperties.getExpiration();
        this.algorithm= jwtProperties.getAlgorithm();
    }

    // subject(사용자 식별자), role(사용자 권한) 등의 Claim, 해시 암호화 알고리즘 포함한 JWT 생성
    public String generateToken(String subject, Map<String, Object> claims){
        long now = System.currentTimeMillis();
        Date expiry = new Date(now + expiration);

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");

        return Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(expiry)
                .signWith(secretKey, algorithm)
                .compact();
    }

}
