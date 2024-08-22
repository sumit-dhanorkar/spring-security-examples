package com.sumit.sumit.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sumit.sumit.Dao.UserDao;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String SCRIPT_TAG_PATTERN = "(?i)<script[^>]*>.*?</script>";


    @Autowired
    private final UserDao userDao;

    @Autowired
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("AUTHORIZATION");
        final String userEmail;
        final String jwtToken;

        CachedBodyHttpServletRequest cachedBodyHttpServletRequest =
                new CachedBodyHttpServletRequest(request);

        CachedBodyHttpServletResponse cachedBodyHttpServletResponse=
                new CachedBodyHttpServletResponse(response);

        String body = cachedBodyHttpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("ans ->" + body);
        ObjectMapper mapper = new ObjectMapper();
        if (containsScriptTag(body)) {

            var problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
            problemDetail.setTitle("Enter Valid Data");
            problemDetail.setDetail("Enter Valid Data!! Data contains scripts ");
//            problemDetail.setStatus((int) Constants.ERROR_CODES.VERSION_MISMATCH);
            response.setStatus(401);
            mapper.writeValue(cachedBodyHttpServletResponse.getWriter(), problemDetail);
            System.out.println("contains scripts ");
            filterChain.doFilter(cachedBodyHttpServletRequest, cachedBodyHttpServletResponse);

            return;
        }



        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(cachedBodyHttpServletRequest, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        userEmail = jwtUtils.extractUsername(jwtToken);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDao.findUserByEmail(userEmail);
//			final boolean isTokenValid;
            if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }


        filterChain.doFilter(cachedBodyHttpServletRequest, cachedBodyHttpServletResponse);

    }


    public static boolean containsScriptTag(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(SCRIPT_TAG_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }


}
