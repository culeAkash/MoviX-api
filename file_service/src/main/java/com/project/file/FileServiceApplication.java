package com.project.file;

import org.apache.logging.log4j.util.PropertyFilePropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication
public class FileServiceApplication {

	public static void main(String[] args) {



		SpringApplication.run(FileServiceApplication.class, args);
	}

}
