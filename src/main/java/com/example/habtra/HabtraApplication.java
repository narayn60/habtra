package com.example.habtra;

import config.RestSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RestSecurityConfig.class)
public class HabtraApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabtraApplication.class, args);
	}

}
