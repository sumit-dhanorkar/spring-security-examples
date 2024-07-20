package com.javaTechie.project.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.javaTechie.project.config.UserInfoUserDetailsService;
import com.javaTechie.project.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserInfoUserDetailsService userInfoUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader=request.getHeader("Authorization");
		String token=null;
	    String username=null;
		//Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdW1pdCIsImlhdCI6MTY4MDE3NDE4MSwiZXhwIjoxNjgwMTc1OTgxfQ.SAQwtpwjy03W87BPG92mj9aU-BBbWYOnD2xvEVF4Bcc
		if(authHeader!=null && authHeader.startsWith("Bearer")) {
			token=authHeader.substring(7);
			username=this.jwtService.extractUserName(token);
		}
		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails= userInfoUserDetailsService.loadUserByUsername(username);
			if(jwtService.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken=
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
}
