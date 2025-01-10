package com.sumit.api;

import com.sumit.model.Book;
import com.sumit.model.Users;
import com.sumit.repo.BookRepository;
import com.sumit.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.IdGenerator;
import reactor.core.publisher.Mono;

@Component
public class BookResourceAssembler {

  private final ModelMapper modelMapper;
  private final IdGenerator idGenerator;
  private final UserRepository userRepository;

  public BookResourceAssembler(ModelMapper modelMapper, IdGenerator idGenerator, UserRepository userRepository) {
    this.modelMapper = modelMapper;
    this.idGenerator = idGenerator;
    this.userRepository = userRepository;
  }

  public Mono<BookResource> toResource(Book book) {
    BookResource bookResource = modelMapper.map(book, BookResource.class);

    if (book.getBorrowedBy() != null) {
      return userRepository.findById(book.getBorrowedBy())
              .map(user -> {
                bookResource.setBorrowedBy(modelMapper.map(user, UserResource.class));
                return bookResource;
              })
              .defaultIfEmpty(bookResource);
    }

    return Mono.just(bookResource);
  }

  public Book toModel(BookResource bookResource) {
    Book book = modelMapper.map(bookResource, Book.class);

    UserResource borrowedBy = bookResource.getBorrowedBy();
    if (borrowedBy != null) {
      Users user = modelMapper.map(borrowedBy, Users.class);
      book.doBorrow(user.getId());
    }

    if (book.getId() == null) {
      book.setId(idGenerator.generateId());
    }

    return book;
  }
}
