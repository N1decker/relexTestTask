package ru.nidecker.relexTestTask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;

@Slf4j
@EnableCaching
@SpringBootApplication
public class RelexTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(RelexTestTaskApplication.class, args);
        openSwaggerUI();
    }

    private static void openSwaggerUI() {
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8080/swagger-ui.html");
        } catch (IOException e) {
            log.error("Couldn't launch Swagger in Chrome");
        }
    }
}
