package com.sumit.config;

import com.sumit.common.Role;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

import java.net.URI;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {

  public final LibraryReactiveUserDetailsService libraryReactiveUserDetailsService;

  public WebSecurityConfiguration(LibraryReactiveUserDetailsService libraryReactiveUserDetailsService) {
      this.libraryReactiveUserDetailsService = libraryReactiveUserDetailsService;
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    return http
//         .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(e-> e
                .matchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                .matchers(EndpointRequest.to("health"))
                .permitAll()
                .matchers(EndpointRequest.to("info"))
                .permitAll()
                .matchers(EndpointRequest.toAnyEndpoint())
                .hasRole(Role.LIBRARY_ADMIN.name())
                .pathMatchers(HttpMethod.POST, "/books/{bookId}/borrow")
                .hasRole(Role.LIBRARY_USER.name())
                .pathMatchers(HttpMethod.POST, "/books/{bookId}/return")
                .hasRole(Role.LIBRARY_USER.name())
                .pathMatchers(HttpMethod.POST, "/books")
                .hasRole(Role.LIBRARY_CURATOR.name())
                .pathMatchers(HttpMethod.DELETE, "/books")
                .hasRole(Role.LIBRARY_CURATOR.name())
                .pathMatchers("/users/**")
                .hasRole(Role.LIBRARY_ADMIN.name()).anyExchange().authenticated()
            ).authenticationManager(authenticationManager())
             .httpBasic(withDefaults())
             .formLogin(withDefaults())
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(logoutSuccessHandler())
            )
             .build();
  }

  @Bean
  public ReactiveAuthenticationManager authenticationManager() {
    UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
            new UserDetailsRepositoryReactiveAuthenticationManager(libraryReactiveUserDetailsService);
    authenticationManager.setPasswordEncoder(passwordEncoder());

    return authenticationManager;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    DelegatingPasswordEncoder encoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
    encoder.setDefaultPasswordEncoderForMatches(new MessageDigestPasswordEncoder("MD5")); // Default to MD5 for unprefixed passwords
    return encoder;
  }



  @Bean
  public ServerLogoutSuccessHandler logoutSuccessHandler() {
    RedirectServerLogoutSuccessHandler logoutSuccessHandler =
            new RedirectServerLogoutSuccessHandler();
    logoutSuccessHandler.setLogoutSuccessUrl(URI.create("/books"));
    return logoutSuccessHandler;
  }
}

