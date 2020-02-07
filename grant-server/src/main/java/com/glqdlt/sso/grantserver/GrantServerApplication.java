package com.glqdlt.sso.grantserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class GrantServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GrantServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
