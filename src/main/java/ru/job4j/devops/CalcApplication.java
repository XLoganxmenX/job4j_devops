package ru.job4j.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения рассчетов, использующего Spring Boot.
 */
@SpringBootApplication
public class CalcApplication {
	/**
	 * Точка входа в Spring Boot приложения.
	 * @param args Аргументы командной строки, передаваемые приложению.
	 */
	public static void main(String[] args) {
		SpringApplication.run(CalcApplication.class, args);
	}
}
