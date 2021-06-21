package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.homework.service.exception.TestRunnerException;

@SpringBootApplication
public class Main {

	public static void main(String[] args) throws TestRunnerException {
		SpringApplication.run(Main.class, args);
	}

}
