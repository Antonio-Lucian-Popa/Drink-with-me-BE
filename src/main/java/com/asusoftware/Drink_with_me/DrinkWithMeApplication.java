package com.asusoftware.Drink_with_me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DrinkWithMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrinkWithMeApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")  // Allow all paths
						.allowedOrigins("*")  // Allow your Angular app
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow these methods
						.allowedHeaders("*")  // Allow all headers
						.exposedHeaders("Authorization") // Expose specific headers to the client
						.allowCredentials(true);  // Allow credentials like cookies
			}
		};
	}
}
