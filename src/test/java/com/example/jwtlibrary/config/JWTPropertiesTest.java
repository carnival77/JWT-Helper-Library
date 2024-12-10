package com.example.jwtlibrary.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// JWTProperties가 정상 빈 주입되었는지, 기본 값이 로딩되는지 확인
// yml에 설정한 값 검증
@SpringBootTest
public class JWTPropertiesTest {

    @Autowired
    JWTProperties jwtProperties;

    @Test
    void propertiesTest(){

        assert jwtProperties.getSecretKey().equals("your-256-bit-secret-your-256-bit-secret-your-256-bit-secret");
        assert jwtProperties.getExpiration() > 0;
        assert jwtProperties.getAlgorithm().equals(SignatureAlgorithm.HS256);
    }
}
