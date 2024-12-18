package com.example.jwtlibrary.util;

import io.jsonwebtoken.Claims;

import java.util.Objects;

//  Claims 객체에서 특정 Claim 값을 안전하게 추출하는 헬퍼 유틸.
public class ClaimsExtractor {

    // 문자열 Claim 추출 시 null 처리 등 안전한 접근 제공.
    public static String getStringClaims(Claims claims, String claimKey) {
        Object value = claims.get(claimKey);
        // Claim이 존재하지 않거나 값이 null인 경우 예외 발생
        Objects.requireNonNull(value, "value is null or not found: " + claimKey);
        return value.toString();
    }

    // 토큰의 Subject(대개 사용자 ID)를 쉽게 가져올 수 있도록 한 헬퍼 메서드.
    public static String getSubject(Claims claims) {
        return claims.getSubject();
    }

}
