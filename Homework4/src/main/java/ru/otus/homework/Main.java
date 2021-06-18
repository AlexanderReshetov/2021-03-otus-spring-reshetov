package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework.service.TestRunnerException;
import ru.otus.homework.service.TestRunnerService;

@SpringBootApplication
public class Main {

	public static void main(String[] args) throws TestRunnerException {
		SpringApplication.run(Main.class, args);
	}

}
