//package com.sumit.poc;
//
//import com.sumit.config.LibraryUsers;
//import com.sumit.model.Users;
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//import java.util.Collection;
//import java.util.Date;
//
//import static java.util.stream.Collectors.joining;
//
//@Component
//public class JwtTokenProvider {
//
//    private static final String AUTHORITIES_KEY = "roles";
//
//    ApplicationPropertites applicationProperties;
//
//    private SecretKey secretKey;
//
//    @PostConstruct
//    protected void init() {
//        var secret = Base64.getEncoder().encodeToString(applicationProperties.getSecretKey().getBytes());
//        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//    }
//
//    public String createToken(LibraryUsers authentication) {
//        String username = authentication.getEmail();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Claims claims = Jwts.claims().subject(username).build();
//        claims.put(AUTHORITIES_KEY, authorities.stream().map(GrantedAuthority::getAuthority).collect(joining(",")));
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + applicationProperties.getValidityInMs());
//        return Jwts.builder()//
//                .setClaims(claims)//
//                .setIssuedAt(now)//
//                .setExpiration(validity)//
//                .signWith(secretKey, SignatureAlgorithm.HS256)//
//                .compact();
//    }
//
//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
//        Collection<? extends GrantedAuthority> authorities = AuthorityUtils
//                .commaSeparatedStringToAuthorityList(claims.get(AUTHORITIES_KEY).toString());
//        User principal = new User(claims.getSubject(), "", authorities);
//        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
//            if (claims.getBody().getExpiration().before(new Date())) {
//                return false;
//            }
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            System.out.println("Invalid JWT token.");
//            System.out.println("Invalid JWT token trace."+e);
//        }
//        return false;
//    }
//}