package com.sumit.service;

import com.sumit.common.Role;
import com.sumit.config.LibraryUsers;
import com.sumit.repo.BookRepository;
import com.sumit.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class LibraryAccessEvaluator {
    private final BookRepository bookRepository;

    public LibraryAccessEvaluator(UserRepository userRepository, BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public boolean hasAccessToBook(Authentication authentication, UUID bookId) {
        LibraryUsers user = (LibraryUsers) authentication.getPrincipal();

        return Boolean.TRUE.equals(bookRepository.findById(bookId)
                .map(book -> {
                    if (!user.getRoles().contains(Role.LIBRARY_USER)) {
                        return false;
                    }

                    if (!book.getMinimumYear().equals(user.getYear())) {
                        return false;
                    }

                    return user.getBranch().equals(book.getBranch());
                })
                .defaultIfEmpty(false).block());
    }
}