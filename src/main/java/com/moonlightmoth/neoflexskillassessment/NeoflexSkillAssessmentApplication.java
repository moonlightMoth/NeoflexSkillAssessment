package com.moonlightmoth.neoflexskillassessment;

import jakarta.websocket.server.ServerEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class NeoflexSkillAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeoflexSkillAssessmentApplication.class, args);
	}
}
