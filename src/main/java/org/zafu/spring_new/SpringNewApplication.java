package org.zafu.spring_new;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNewApplication.class, args);
    }
}
