package com.Parfetch.ParFetch.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordGeneratorConfig {

    @Bean
    public CommandLineRunner passwordLogger() {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String rawPassword = "staff123"; // change this to whatever you want
            String encodedPassword = encoder.encode(rawPassword);
            System.out.println("====================================");
            System.out.println("Staff user password (plain): " + rawPassword);
            System.out.println("Staff user password (bcrypt): " + encodedPassword);
            System.out.println("====================================");
        };
    }
}
