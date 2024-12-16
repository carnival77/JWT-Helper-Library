package com.example.jwtlibrary.service;

import com.example.jwtlibrary.config.JWTProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

public class JWTGenerator {

    private final JWTProperties jwtProperties;

    // JWTProperties를 주입받음
    public JWTGenerator(JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * JWT 토큰 생성
     * @param useCase 사용 사례 (예: create-account, login)
     * @param subject 토큰의 주제 (예: 사용자 ID)
     * @param additionalClaims 추가 클레임
     * @return 생성된 JWT 토큰
     */
    public String generateToken(String useCase, String subject, Map<String,Object> claims, Map<String, Object> additionalClaims) {
        // 사용 사례에 해당하는 설정 가져오기
        JWTProperties.JwtConfig config = jwtProperties.getCases().get(useCase);
        if (config == null) {
            throw new IllegalArgumentException("Invalid use case: " + useCase);
        }

        long now = System.currentTimeMillis(); // 현재 시간
        Date expiry = new Date(now + config.getExpiration()); // 만료 시간 계산

        // 클레임 설정
        // YAML에서 정의된 기본 claims에 대한 값 존재 여부 검사
        if (config.getClaims() != null) {
            // 필수 claims가 모두 존재하는지 검사
            for (String claimKey : config.getClaims()) {
                if(!claims.containsKey(claimKey)){
                    throw new IllegalArgumentException("필수로 설정된 claims가 누락되었습니다.: " + claims);
                }
            }
            // 필수 claims와 입력된 claims의 key가 일치하는 지 검사
            String[] NecessaryClaimsKeys= config.getClaims();
            String[] gotClaimsKeys = claims.keySet().toArray(new String[0]);
            Arrays.sort(NecessaryClaimsKeys);
            Arrays.sort(gotClaimsKeys);
            if(!Arrays.equals(NecessaryClaimsKeys,gotClaimsKeys)){
                throw new IllegalArgumentException("필수로 설정된 claims와 입력된 claims가 다릅니다.: " + claims);
            }
            // 필수 claims에 대한 유효한 값들이 모두 존재하는지 검사
            for(String claimKey : config.getClaims()){
                if(claims.get(claimKey) == null || claims.get(claimKey).toString().isEmpty()){
                    throw new IllegalArgumentException("필수로 설정된 claims에 대한 유효한 값이 존재하지 않습니다.: " + claims);
                }
            }
        }
        // YAML에서 정의된 기본 claims와 추가 claims 병합
        if (additionalClaims != null) {
            claims.putAll(additionalClaims); // 추가 클레임 병합
        }

        Key secretKey = Keys.hmacShaKeyFor(config.getSecretKey().getBytes(StandardCharsets.UTF_8));

        // JWT 빌더를 사용하여 토큰 생성
        return Jwts.builder()
                .setHeaderParam("typ", "JWT") // 헤더
                .setClaims(claims)           // 클레임
                .setSubject(subject)         // 주제
                .setIssuedAt(new Date(now))  // 발급 시간
                .setExpiration(expiry)       // 만료 시간
                .signWith(SignatureAlgorithm.valueOf(config.getAlgorithm()), secretKey) // 서명
                .compact();                  // JWT 문자열 반환
    }

}
