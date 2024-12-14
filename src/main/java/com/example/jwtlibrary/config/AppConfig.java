package com.example.jwtlibrary.config;

import com.example.jwtlibrary.service.JWTGenerator;
import com.example.jwtlibrary.service.JWTService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = "com.example.jwtlibrary")
// AppConfig가 application.properties를 로딩하고,
// PropertySourcesPlaceholderConfigurer를 통해 ${jwt.secretKey} 등의 플레이스홀더를 해결한 뒤,
// jwtProperties() 메서드에서 생성한 JWTProperties 빈에 값을 주입
@PropertySource("classpath:application.properties")
public class AppConfig {

    // Spring Context에서 @Value를 사용하기 위한 설정
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    // @Service 어노테이션을 통한 의존관계 자동 주입이 아닌 JWTProperties, JWTGenerator, JWTService 빈 수동 등록
    @Bean
    public JWTProperties jwtProperties(){
        return new JWTProperties();
    }

    @Bean
    public JWTGenerator jwtGenerator(JWTProperties jwtProperties){
        return new JWTGenerator(jwtProperties);
    }

    @Bean
    public JWTService jwtService(JWTProperties jwtProperties){
        return new JWTService(jwtProperties);
    }

}
