package com.example.jwtlibrary.service;

import com.example.jwtlibrary.config.JWTProperties;
import com.example.jwtlibrary.exception.InvalidTokenException;
import com.example.jwtlibrary.exception.MissingClaimsException;
import com.example.jwtlibrary.exception.TokenExpiredException;
import com.example.jwtlibrary.util.ClaimsExtractor;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Objects;

// JWT 토큰 파싱, 서명 검증, 만료 검증 로직을 중앙화한 핵심 서비스 클래스.
public class JWTService {

    private final JWTProperties jwtProperties;

    public JWTService(JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    // 토큰 파싱, 서명 검증, 만료 검증 수행
    // 유효하지 않을 경우 예외 발생
    public Claims validateAndClaims(String token, String useCase){
        // 사용 사례에 해당하는 설정 가져오기
        JWTProperties.JwtConfig config = jwtProperties.getCases().get(useCase);
        Key secretKey = Keys.hmacShaKeyFor(config.getSecretKey().getBytes(StandardCharsets.UTF_8));

        try{
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            // 만료 시간 자동 검증
            Claims claims = jws.getBody();

            // 필수 Claim 확인 (ex. Subject)
            if (claims.getSubject()==null){
                throw new MissingClaimsException("Subject claim is missing");
            }

            return claims;
        } catch(ExpiredJwtException e){
            throw new TokenExpiredException("Token is expired");
        } catch(UnsupportedJwtException | MalformedJwtException | SignatureException e){
            throw new InvalidTokenException("Invalid token: "+e.getMessage());
        } catch(IllegalArgumentException e){
            throw new InvalidTokenException("Token is empty or null");
        }
    }

    public String getUserRoleFromClaims(Claims claims, String useCase){
        // 사용 사례에 해당하는 설정 가져오기
        JWTProperties.JwtConfig config = jwtProperties.getCases().get(useCase);
        // 사용 사례에 필수 claims 부재 시
        if (config.getClaims() == null || config.getClaims().length == 0){
            throw new IllegalArgumentException("Claims are not defined for use case: "+useCase);
        }

        return ClaimsExtractor.getStringClaims(claims, "role");
    }

}
