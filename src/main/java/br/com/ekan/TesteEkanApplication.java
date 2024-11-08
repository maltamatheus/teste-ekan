package br.com.ekan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class TesteEkanApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteEkanApplication.class, args);
	}

}
