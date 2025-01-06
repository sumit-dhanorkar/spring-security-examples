//package com.sumit.poc;
//
//import com.sumit.config.LibraryReactiveUserDetailsService;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.util.StringUtils;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//public class JwtAuthenticationFilter implements WebFilter {
//    private final JwtService jwtService;
//    private final LibraryReactiveUserDetailsService userDetailsService;
//
//    public JwtAuthenticationFilter(JwtService jwtService, LibraryReactiveUserDetailsService userDetailsService) {
//        this.jwtService = jwtService;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        String token = extractToken(exchange.getRequest());
//
//        if (token == null) {
//            return chain.filter(exchange);
//        }
//        String username = jwtService.extractUsername(token);
//        return userDetailsService.findByUsername(username)
//                    .filter(userDetails -> jwtService.isTokenValid(token, userDetails))
//                    .flatMap(userDetails -> {
//
//                        UsernamePasswordAuthenticationToken auth =
//                                new UsernamePasswordAuthenticationToken(
//                                        userDetails, null, userDetails.getAuthorities());
//                        return chain.filter(exchange)
//                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
//                    });
//    }
//
//    private String extractToken(ServerHttpRequest request) {
//        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}
//
