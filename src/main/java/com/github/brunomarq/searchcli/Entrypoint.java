package com.github.brunomarq.searchcli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Entrypoint {
	static final Logger log = LoggerFactory.getLogger(Entrypoint.class);

	public static void main(String[] args) {
		log.info("Starting SearchCLI based on Spring Shell.");
		SpringApplication.run(Entrypoint.class, args);
		log.info("Closing SearchCLI.");
	}

}
