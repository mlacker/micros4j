package com.mlacker.micros.auth;

import com.mlacker.micros.config.properties.TokenProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@MapperScan(basePackages = "com.mlacker.micros.auth.repository.mapper")
@Import({
        com.mlacker.micros.config.FeignConfig.class,
        com.mlacker.micros.config.MapperConfig.class
})
@EnableConfigurationProperties(TokenProperties.class)
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
