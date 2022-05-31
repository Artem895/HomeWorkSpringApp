package com.netcracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication

public class HomeWorkSpringAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeWorkSpringAppApplication.class, args);
    }

}
