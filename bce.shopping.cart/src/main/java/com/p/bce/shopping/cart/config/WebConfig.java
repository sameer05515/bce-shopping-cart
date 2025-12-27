package com.p.bce.shopping.cart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		// JSP files are in src/main/webapp/WEB-INF/views/ (WAR packaging)
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		resolver.setExposeContextBeansAsAttributes(true);
		resolver.setOrder(1);
		registry.viewResolver(resolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Serve static resources from standard Spring Boot location
		// JSP files are in src/main/webapp/WEB-INF/views/ (WAR packaging)
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/", 
									  "classpath:/META-INF/resources/", 
									  "classpath:/resources/", 
									  "classpath:/public/");
	}
}

