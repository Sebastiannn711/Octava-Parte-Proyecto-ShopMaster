package com.shopmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Inicia la aplicación Spring Boot y publica sus servicios web. */
@SpringBootApplication
public class ShopMasterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopMasterApiApplication.class, args);
    }
}