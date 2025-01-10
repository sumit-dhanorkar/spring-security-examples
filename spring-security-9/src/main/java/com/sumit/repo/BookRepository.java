package com.sumit.repo;

import com.sumit.model.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BookRepository extends ReactiveCrudRepository<Book, UUID> {
    Mono<Book> findOneByBorrowedBy(UUID uuid);
}
