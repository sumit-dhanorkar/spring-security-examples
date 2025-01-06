//package com.sumit.poc;
//
//import com.sumit.repo.UserRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
//import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
//
//@Configuration
//public class SecurityConfig {
//
//
//
//
//    @Bean
//    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http, JwtTokenProvider tokenProvider,
//                                                ReactiveAuthenticationManager reactiveAuthenticationManager) {
//
//        final String PATH_AUTH_TOKEN = "/auth/token";
//
//        return http.csrf(ServerHttpSecurity.CsrfSpec::disable).httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
//                .authenticationManager(reactiveAuthenticationManager)
//                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
//                .authorizeExchange(exchanges -> exchanges.pathMatchers(PATH_AUTH_TOKEN).permitAll())
//                .addFilterAt(new JwtAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.HTTP_BASIC).build();
//    }
//    @Bean
//    public ReactiveUserDetailsService userService(UserRepository users) {
//
//        return username -> users.findOneByEmail(username)
//                .map(u -> User.withUsername(u.getUsername()).password(u.getPassword())
//                        .authorities(u.getAuthorities().toArray(new String[0])).accountExpired(!u.isAccountNonExpired())
//                        .credentialsExpired(!u.isCredentialsNonExpired()).disabled(!u.isEnabled())
//                        .accountLocked(!u.isAccountNonLocked()).build());
//    }
//
//
//    @Bean
//    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
//                                                                       PasswordEncoder passwordEncoder) {
//        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
//        authenticationManager.setPasswordEncoder(passwordEncoder);
//        return authenticationManager;
//    }
//
//}
