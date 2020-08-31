package com.mlacker.micros.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@EnableEurekaClient
//@EnableFeignClients
//@ComponentScan(basePackages = {
//        "com.mlacker.micros.data",
//        "com.mlacker.micros.core",
//        "com.mlacker.micros.core.data"
//})
@MapperScan(basePackages = "com.mlacker.micros.data.repository.mapper")
@Import({
        com.mlacker.micros.config.FeignConfig.class,
        com.mlacker.micros.config.MapperConfig.class
})
public class DataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }
}
