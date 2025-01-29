package com.example.crs;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.crs.model.Student;
import com.example.crs.model.StudentRepository;

@SpringBootApplication
public class CrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrsApplication.class, args);
	}
	@Bean
	ApplicationRunner init(StudentRepository repository){
		return args->{
			repository.save(new Student("Linh","linhne@gmail.com","CSIS"));
			repository.save(new Student("Huy","huyne@gmail.com","CS"));
			repository.findAll().forEach(System.out::println);
		};
	}
}
