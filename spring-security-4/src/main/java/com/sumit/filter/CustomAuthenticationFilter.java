package com.sumit.filter;


import com.sumit.authentication.CustomAuthentication;
import com.sumit.manager.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomAuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. create and authentication object which is not yet authenticated
        //2. delegates the authentication object to authentication manager
        //3. get back the authentication object from the manager
        //4. if the object is authenticated then send the request to the next filter in the filterchain

        System.out.println("CustomAuthenticationFilter.doFilterInternal");

        String key = String.valueOf(request.getHeader("key"));

        CustomAuthentication customAuthentication=new CustomAuthentication(false,key);


        var auth = authenticationManager.authenticate(customAuthentication);

        if(auth.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(auth);

        }
        filterChain.doFilter(request,response); //only when authentication worked
    }
}
