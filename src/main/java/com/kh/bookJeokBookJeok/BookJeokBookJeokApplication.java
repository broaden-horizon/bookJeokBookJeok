package com.kh.bookJeokBookJeok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookJeokBookJeokApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookJeokBookJeokApplication.class, args);
	}

}
