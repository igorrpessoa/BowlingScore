package com.igorrpessoa.bowling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BowlingApplication {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(BowlingApplication.class, args);
    }

}
