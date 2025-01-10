package com.sumit.config;

import com.sumit.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class LibraryUsers extends Users implements UserDetails {

  private static final String ROLE_PREFIX = "ROLE_";

  LibraryUsers(Users users) {
    super(users);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getRoles().stream()
        .map(Enum::name)
        .map(String::toUpperCase)
        .map(rn -> ROLE_PREFIX + rn)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return super.getPassword();
  }

  @Override
  public String getUsername() {
    return getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
