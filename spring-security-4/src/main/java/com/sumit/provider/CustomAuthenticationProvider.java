package com.sumit.provider;

import com.sumit.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${our.very.very.secrete.key}")
    private String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        System.out.println("CustomAuthenticationProvider.authenticate");

        CustomAuthentication ca = (CustomAuthentication) authentication;
        String headerKey= ca.getKey();
        if (key.equals(headerKey)){
            return new CustomAuthentication(true,null);
        }

        throw new BadCredentialsException("you are very very bad..");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("CustomAuthenticationProvider.supports");

        return CustomAuthentication.class.equals(authentication);
    }
}
