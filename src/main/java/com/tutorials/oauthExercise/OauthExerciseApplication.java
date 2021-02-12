package com.tutorials.oauthExercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OauthExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthExerciseApplication.class, args);
	}

}
