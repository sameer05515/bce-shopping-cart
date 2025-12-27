package com.p.bce.shopping.cart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Serve static resources from standard Spring Boot location
		// Thymeleaf templates are in src/main/resources/templates/
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/", 
									  "classpath:/META-INF/resources/", 
									  "classpath:/resources/", 
									  "classpath:/public/");
	}
}

