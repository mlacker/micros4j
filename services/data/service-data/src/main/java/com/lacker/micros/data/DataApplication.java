package com.lacker.micros.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableEurekaClient
//@EnableFeignClients
//@ComponentScan(basePackages = {
//        "com.lacker.micros.data",
//        "com.lacker.micros.core",
//        "com.lacker.micros.core.data"
//})
@MapperScan(basePackages = "com.lacker.micros.data.repository.mapper")
public class DataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }
}
