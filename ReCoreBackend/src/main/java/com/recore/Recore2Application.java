package com.recore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Módulo 2 - Spring Boot
 * Clase 2 - Introducción
 * Clase principal de la aplicación Spring Boot
 */
@SpringBootApplication
@EnableScheduling
public class Recore2Application {

	public static void main(String[] args) {
		SpringApplication.run(Recore2Application.class, args);
	}
}
