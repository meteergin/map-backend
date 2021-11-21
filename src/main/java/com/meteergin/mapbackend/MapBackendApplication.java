package com.meteergin.mapbackend;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class MapBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapBackendApplication.class, args);
    }

}
