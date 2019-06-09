package com.lacker.micros.data;

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
public class DataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }
}
