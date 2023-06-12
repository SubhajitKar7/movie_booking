package com.example.demo;

import com.example.demo.filter.AdminJWT;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.demo.filter.JWTFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
@OpenAPIDefinition
public class MovieBookingAppApplication {


/*
	@Bean
	public FilterRegistrationBean jwtFilter()
	{
		FilterRegistrationBean fb = new FilterRegistrationBean();
		
		fb.setFilter(new JWTFilter());
		
		fb.addUrlPatterns("/api/v1.0/user/*");
		return fb;
	}


	@Bean
	public FilterRegistrationBean adminJWT()
	{
		FilterRegistrationBean fb = new FilterRegistrationBean();

		fb.setFilter(new AdminJWT());

		fb.addUrlPatterns("/api/v1.0/admin/*");
		return fb;
	}

*/

	@Configuration
	class OpenApiConfig
	{
		@Bean
		public OpenAPI customconfig()
		{
			final String securitySchemeName = "bearerAuth";
			
			return new OpenAPI().addSecurityItem(new SecurityRequirement()
					.addList(securitySchemeName)).components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme()
							.name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer")
							.bearerFormat("JWT")));



					}


		
	}

	
	
	public static void main(String[] args) {
		SpringApplication.run(MovieBookingAppApplication.class, args);
	}

}
