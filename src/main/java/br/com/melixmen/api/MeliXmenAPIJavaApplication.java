package br.com.melixmen.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = MeliXmenAPIJavaApplication.class)
public class MeliXmenAPIJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeliXmenAPIJavaApplication.class, args);
	}
}
