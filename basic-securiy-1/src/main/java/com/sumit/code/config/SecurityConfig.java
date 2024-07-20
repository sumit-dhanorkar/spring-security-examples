package com.sumit.code.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity //to provide method level security
public class SecurityConfig {
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.authorizeHttpRequests((authorize)-> authorize.anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults());
		
		
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails sumit=User.builder()
				.username("admin")
				.password(passwordEncoder().encode("admin"))
				.roles("ADMIN")
				.build();
		
		UserDetails ramesh=User.builder()
				.username("ramesh")
				.password(passwordEncoder().encode("ramesh"))
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(sumit,ramesh);
	}
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
