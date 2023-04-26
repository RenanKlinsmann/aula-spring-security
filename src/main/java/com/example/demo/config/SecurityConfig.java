package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
	public PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		/*InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		UserBuilder users = User.builder();
		manager.createUser(users.username("professor").password(passwordEncoder().encode("123")).roles("ADMIN").build());
		manager.createUser(users.username("renan").password(passwordEncoder().encode("123")).roles("USER").build());
		return manager;*/
		UserDetails user = User.builder()
				.username("professor")
				.password(passwordEncoder().encode("123"))
				.roles("ADMIN")
				.build();
		
		UserDetails user2 = User.builder()
				.username("renan")
				.password(passwordEncoder().encode("123"))
				.roles("USER")
				.build();
		
		
		return new InMemoryUserDetailsManager(user, user2);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher("/alunos/**")
			.authorizeHttpRequests(auth -> auth 
					.anyRequest().hasAnyRole("ADMIN", "USER")
					);
		
		
		return http.build();
		
	}
	
	@Bean
	public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorize -> authorize
					.anyRequest().authenticated()
			)
			.formLogin();
		
		
		return http.build();
		
	}

}
