package com.sumit.repo;

import com.sumit.model.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface BookRepository extends ReactiveCrudRepository<Book, UUID> {}
