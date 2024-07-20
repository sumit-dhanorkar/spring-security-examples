package com.sumit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic()
                .and()
//                .authorizeHttpRequests(c->c.anyRequest().authenticated())
                .authorizeHttpRequests()
//                .requestMatchers("/test1").authenticated()
//                .requestMatchers("/test2").permitAll()
//                .requestMatchers("test2").hasAuthority("read")
//                .requestMatchers(HttpMethod.GET,"/demo/**").hasAuthority("read")
                .requestMatchers("/test/test1").authenticated()
                .anyRequest().authenticated()
                .and().csrf().disable() //dont do in real application here are doing only for post method
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        var uds=new InMemoryUserDetailsManager();

        var user1= User.withUsername("sumit")
                .password(passwordEncoder().encode("1234"))
                .authorities("read") //granted authorities
                .build();

        var user2= User.withUsername("amit")
                .password(passwordEncoder().encode("12345"))
                .authorities("write")
                .build();

        uds.createUser(user1);
        uds.createUser(user2);
        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }


}
