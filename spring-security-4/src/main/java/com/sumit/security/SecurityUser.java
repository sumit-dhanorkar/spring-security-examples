//package com.sumit.security;
//
//import com.sumit.entity.User;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@AllArgsConstructor
//public class SecurityUser implements UserDetails {
//
//    @Autowired
//    private User user;
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return user.getAuthorities()
//                .stream()
//                .map(SecurityAuthority::new)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
