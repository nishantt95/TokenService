package com.cclogic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Nishant on 9/16/2017.
 */

@SpringBootApplication
@EnableAutoConfiguration
public class Main {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String args[]) {
        SpringApplication.run(Main.class, args);
    }
}
