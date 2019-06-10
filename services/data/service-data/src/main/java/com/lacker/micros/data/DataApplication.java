package com.lacker.micros.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@EnableEurekaClient
//@EnableFeignClients
//@ComponentScan(basePackages = {
//        "com.lacker.micros.data",
//        "com.lacker.micros.core",
//        "com.lacker.micros.core.data"
//})
@MapperScan(basePackages = "com.lacker.micros.data.repository.mapper")
@Import({
        com.lacker.micros.config.MapperConfig.class
})
public class DataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }
}
