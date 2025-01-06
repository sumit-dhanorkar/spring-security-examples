package com.sumit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig  {

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService(){

        var u1= User.withUsername("sumit")
                .password(passwordEncoder().encode("12345"))
                .authorities("read")
                .build();


        return new MapReactiveUserDetailsService(u1);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http){
        return http
                .httpBasic()
                .and()
                .authorizeExchange()
                .pathMatchers("/demo/**").authenticated()
                .and()
                .build();
    }



}
