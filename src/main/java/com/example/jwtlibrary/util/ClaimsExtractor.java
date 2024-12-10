package com.example.jwtlibrary.util;

import io.jsonwebtoken.Claims;

//  Claims 객체에서 특정 Claim 값을 안전하게 추출하는 헬퍼 유틸.
public class ClaimsExtractor {

    // 문자열 Claim 추출 시 null 처리 등 안전한 접근 제공.
    public static String getStringClaims(Claims claims, String claimKey) {
        Object value = claims.get(claimKey);
        return value != null ? value.toString() : null;
    }

    // 토큰의 Subject(대개 사용자 ID)를 쉽게 가져올 수 있도록 한 헬퍼 메서드.
    public static String getSubject(Claims claims) {
        return claims.getSubject();
    }

}
