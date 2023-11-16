package com.schmitt.reactive.java.src.application;

import com.schmitt.reactive.java.src.config.RecordProcessControllerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RecordProcessControllerConfig.class)
public class ReactiveJavaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveJavaServiceApplication.class, args);
	}

}
