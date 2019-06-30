package com.lacker.micros.auth;

import com.lacker.micros.config.properties.TokenProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@MapperScan(basePackages = "com.lacker.micros.auth.repository.mapper")
@Import({
        com.lacker.micros.config.MapperConfig.class
})
@EnableConfigurationProperties(TokenProperties.class)
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
