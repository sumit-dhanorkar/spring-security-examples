package com.sumit.api;

import com.sumit.config.LibraryUsers;
import com.sumit.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


/** REST api for books. */
@RestController
@Validated
public class BookRestController {

  private static final String PATH_VARIABLE_BOOK_ID = "bookId";

  private static final String PATH_BOOK_ID = "{" + PATH_VARIABLE_BOOK_ID + "}";

  private final BookService bookService;

  private final BookResourceAssembler bookResourceAssembler;

  @Autowired
  public BookRestController(BookService bookService, BookResourceAssembler bookResourceAssembler) {
    this.bookService = bookService;
    this.bookResourceAssembler = bookResourceAssembler;
  }

  @GetMapping("/books")
  public Flux<BookResource> getAllBooks() {
    return bookService.findAll().map(bookResourceAssembler::toResource);
  }

  @GetMapping("/books/" + PATH_BOOK_ID)
  public Mono<ResponseEntity<BookResource>> getBookById(
          @PathVariable(PATH_VARIABLE_BOOK_ID) UUID bookId) {
    return bookService
            .findById(bookId)
            .map(bookResourceAssembler::toResource)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping("/books/" + PATH_BOOK_ID + "/borrow")
  public Mono<Void> borrowBookById(
          @PathVariable(PATH_VARIABLE_BOOK_ID) UUID bookId, @AuthenticationPrincipal LibraryUsers user) {
    return bookService.borrowById(bookId, user != null ? user.getId() : null);
  }

  @PostMapping("/books/" + PATH_BOOK_ID + "/return")
  public Mono<Void> returnBookById(
          @PathVariable(PATH_VARIABLE_BOOK_ID) UUID bookId, @AuthenticationPrincipal LibraryUsers user) {
    return bookService.returnById(bookId, user != null ? user.getId() : null);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/books")
  public Mono<Void> createBook(@RequestBody Mono<BookResource> bookResource) {
    return bookService.create(bookResource.map(bookResourceAssembler::toModel));
  }

  @DeleteMapping("/books/" + PATH_BOOK_ID)
  public Mono<Void> deleteBook(@PathVariable(PATH_VARIABLE_BOOK_ID) UUID bookId) {
    return bookService.deleteById(bookId);
  }
}

