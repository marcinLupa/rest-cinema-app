package com.cinema;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javassist.bytecode.SignatureAttribute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.crypto.SecretKey;
import java.security.KeyStore;

@PropertySource("classpath:application.properties")
@SpringBootApplication
public class RestCinemaApp {

    public static void main(String[] args) {
        SpringApplication.run(RestCinemaApp.class, args);
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
