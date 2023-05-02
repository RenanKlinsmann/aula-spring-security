package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.UsuarioServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
	public PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http,
													   PasswordEncoder passwordEncoder,
													   UsuarioServiceImpl userDetails) throws Exception {
		return http
				.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetails)
				.passwordEncoder(passwordEncoder)
				.and()
				.build();
		
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeHttpRequests(auth -> auth 
					.requestMatchers("/alunos/**")
						.hasAnyRole("ADMIN")
					.requestMatchers(HttpMethod.POST, "/usuarios/**")
						.permitAll()
					.anyRequest().authenticated()
					).httpBasic();

		return http.build();
		
	}

}
