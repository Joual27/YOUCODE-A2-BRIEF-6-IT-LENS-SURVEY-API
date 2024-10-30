package org.youcode.ITLens;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "org.youcode.ITLens")
public class ItLensApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItLensApplication.class, args);
	}
}
