package org.chkaur99;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RateLimitedApiApplication {
    public static void main(String[] args){
        SpringApplication.run(RateLimitedApiApplication.class, args);
    }
}
