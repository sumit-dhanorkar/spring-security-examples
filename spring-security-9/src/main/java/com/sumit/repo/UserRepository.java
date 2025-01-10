package com.sumit.repo;

import com.sumit.model.Users;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository<Users, UUID> {
    Mono<Users> findOneByEmail(String email);
}
