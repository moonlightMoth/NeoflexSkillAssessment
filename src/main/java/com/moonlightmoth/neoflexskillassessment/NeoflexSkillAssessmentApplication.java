package com.moonlightmoth.neoflexskillassessment;

import com.moonlightmoth.neoflexskillassessment.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class NeoflexSkillAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeoflexSkillAssessmentApplication.class, args);
	}
}
