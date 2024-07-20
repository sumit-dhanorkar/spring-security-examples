//package com.sumit.services;
//
//import com.sumit.repositories.UserRepo;
//import com.sumit.security.SecurityUser;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailService implements UserDetailsService {
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username)  {
//
//        var u=userRepo.findUserByUsername(username);
//
//        return u.map(SecurityUser::new)
//                .orElseThrow(()->new UsernameNotFoundException("Username not found "+username));
//    }
//}
