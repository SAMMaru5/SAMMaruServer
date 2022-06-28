package com.sammaru5.sammaru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SammaruApplication {

	public static void main(String[] args) {
		SpringApplication.run(SammaruApplication.class, args);
	}

}
