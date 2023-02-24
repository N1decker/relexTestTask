package ru.nidecker.relexTestTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class RelexTestTaskApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(RelexTestTaskApplication.class, args);
		openSwaggerUI();
	}

	private static void openSwaggerUI() throws IOException {
		Runtime rt = Runtime.getRuntime();
		rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8080/swagger-ui.html");
	}
}
