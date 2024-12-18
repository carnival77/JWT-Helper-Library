package com.example.jwtlibrary.config;

import com.example.jwtlibrary.service.JWTGenerator;
import com.example.jwtlibrary.service.JWTService;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "com.example.jwtlibrary")
// AppConfig가 application.yml을 로딩하고, YAML 데이터를 DTO 객체(JWTProperties)로 변환한 후
// jwtProperties() 메서드에서 생성한 JWTProperties 빈에 값을 주입
public class AppConfig {

    // @Service 어노테이션을 통한 의존관계 자동 주입이 아닌 JWTProperties, JWTGenerator, JWTService 빈 수동 등록
    /**
     * YAML 파일(application.yml)을 읽어서 JWTProperties로 매핑
     */
    @Bean
    public JWTProperties jwtProperties() {
        JWTProperties jwtProperties = new JWTProperties();

        // YAML 파일을 Map으로 변환
        YamlMapFactoryBean yamlFactory = new YamlMapFactoryBean();
        yamlFactory.setResources(new ClassPathResource("application.yml")); // YAML 파일 경로
        Map<String, Object> yamlData = yamlFactory.getObject(); // YAML 데이터를 읽어 Map으로 반환

        if (yamlData != null && yamlData.containsKey("jwt")) {
            // jwt.cases를 가져옴
            Map<String, Map<String, Object>> casesMap = (Map<String, Map<String, Object>>) yamlData.get("jwt");

            Map<String, JWTProperties.JwtConfig> cases = new HashMap<>();

            // 각 사용 사례별 설정을 DTO에 매핑
            for (Map.Entry<String, Map<String, Object>> entry : casesMap.entrySet()) {
                String useCase = entry.getKey(); // 사용 사례 이름 (예: create-account)
                Map<String, Object> configMap = entry.getValue();

                // JWT 설정 매핑
                JWTProperties.JwtConfig config = new JWTProperties.JwtConfig();
                config.setSecretKey((String) configMap.get("secretKey"));
                config.setExpiration(((Number) configMap.get("expiration")).longValue());
                config.setAlgorithm((String) configMap.get("algorithm"));

                if (configMap.containsKey("claims")) {
                    // claims를 배열로 변환
                    config.setClaims(((java.util.List<String>) configMap.get("claims")).toArray(new String[0]));
                }

                cases.put(useCase, config);
            }

            jwtProperties.setCases(cases);
        }

        return jwtProperties;
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
