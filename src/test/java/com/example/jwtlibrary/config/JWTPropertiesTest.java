package com.example.jwtlibrary.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// JWTProperties가 정상 빈 주입되었는지, 기본 값이 로딩되는지 확인
// yml에 설정한 값 검증
public class JWTPropertiesTest {

    @Test
    void propertiesTest(){

        //Spring Context 수동 초기화
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // JWTProperties 빈 주입
        JWTProperties jwtProperties = context.getBean(JWTProperties.class);

        assert jwtProperties.getSecretKey().equals("your-256-bit-secret-your-256-bit-secret-your-256-bit-secret");
        assert jwtProperties.getExpiration() > 0;
        assert jwtProperties.getAlgorithm().equals(SignatureAlgorithm.HS256);

        context.close();
    }
}
