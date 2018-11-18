package br.com.projectBackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;
import java.nio.charset.*;

@SpringBootApplication
public class ProjectBackEndApplication {

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC-3"));   // It will set UTC timezone
		System.out.println("Spring boot application running in UTC timezone :"+ new Date());   // It will print UTC timezone
	}

	public static void main(String[] args) {

		SpringApplication.run(ProjectBackEndApplication.class, args);
	}
}
