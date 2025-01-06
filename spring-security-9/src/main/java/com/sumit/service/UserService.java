package com.sumit.service;

import com.sumit.model.Users;
import com.sumit.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@PreAuthorize("hasRole('LIBRARY_ADMIN')")
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PreAuthorize("isAnonymous() or isAuthenticated()")
  public Mono<Users> findOneByEmail(String email) {
    return userRepository.findOneByEmail(email);
  }

  public Mono<Void> create(Mono<Users> user) {
    return user.flatMap(userRepository::save).then();
  }

  public Mono<Users> update(Users user) {
    return userRepository.save(user);
  }

  public Mono<Users> findById(UUID uuid) {
    return userRepository.findById(uuid);
  }

  public Flux<Users> findAll() {
    return userRepository.findAll();
  }

  public Mono<Void> deleteById(UUID uuid) {
    return userRepository.deleteById(uuid);
  }
}
