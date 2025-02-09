package ru.job4j.devops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * �������� ����� ���������� ���������, ������������� Spring Boot.
 */
@SpringBootApplication
public class CalcApplication {
	/**
	 * ����� ����� � Spring Boot ����������.
	 * @param args ��������� ��������� ������, ������������ ����������.
	 */
	public static void main(String[] args) {
		SpringApplication.run(CalcApplication.class, args);
	}
}
