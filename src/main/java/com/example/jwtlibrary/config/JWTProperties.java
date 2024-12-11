package com.example.jwtlibrary.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/*
  `application.yml`에 정의된 `jwt.secret`, `jwt.expiration`, `jwt.algorithm` 값을 스프링 빈으로 로딩하는 설정 클래스.
- Spring Boot 애플리케이션 시작 시점에 빈으로 등록되어 `JWTService`, `JWTGenerator` 등의 클래스에서 주입받아 사용한다.
- 이로써 시크릿 키나 만료 시간 같은 중요 설정을 코드에서 하드코딩하지 않고 환경 설정으로 관리 가능.
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {
    private String secretKey;
    private long expiration;
    private SignatureAlgorithm algorithm;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public SignatureAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(SignatureAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
}
