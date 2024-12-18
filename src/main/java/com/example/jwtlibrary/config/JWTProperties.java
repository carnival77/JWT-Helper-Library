package com.example.jwtlibrary.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/*
  `application.yml`에 정의된 YAML 파일의 데이터를 매핑하는 클래스입니다.
각 사용 사례(cases)와 JWT 설정(JwtConfig)을 정의합니다.
- Spring Boot 애플리케이션 시작 시점에 빈으로 등록되어 `JWTService`, `JWTGenerator` 등의 클래스에서 주입받아 사용한다.
- 이로써 시크릿 키나 만료 시간 같은 중요 설정을 코드에서 하드코딩하지 않고 환경 설정으로 관리 가능.
 */
public class JWTProperties {

    // YAML의 사용 사례별 설정을 저장할 Map (예: "create-account", "login")
    private Map<String, JwtConfig> cases;

    // 사용 사례별 설정 (Getter/Setter)
    public Map<String, JwtConfig> getCases() {
        return cases;
    }

    public void setCases(Map<String, JwtConfig> cases) {
        this.cases = cases;
    }

    // JWT 설정을 담는 내부 클래스
    public static class JwtConfig {
        private String secretKey; // 비밀 키
        private Long expiration;  // 만료 시간 (밀리초)
        private String algorithm; // 서명 알고리즘 (예: HS256)
        private String[] claims;  // 기본 클레임 배열

        // Getter/Setter for 각 설정 필드
        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public Long getExpiration() {
            return expiration;
        }

        public void setExpiration(Long expiration) {
            this.expiration = expiration;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String[] getClaims() {
            return claims;
        }

        public void setClaims(String[] claims) {
            this.claims = claims;
        }
    }
}
