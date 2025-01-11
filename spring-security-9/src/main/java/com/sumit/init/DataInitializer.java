package com.sumit.init;

import com.sumit.common.Role;
import com.sumit.model.Book;
import com.sumit.model.Users;
import com.sumit.repo.BookRepository;
import com.sumit.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

/** Store initial users and books in mongodb. */
@Component
public class DataInitializer implements CommandLineRunner {

  private static final UUID USER_WAYNE_IDENTIFIER =
          UUID.fromString("c47641ee-e63c-4c13-8cd2-1c2490aee0b3");
  private static final UUID USER_BANNER_IDENTIFIER =
          UUID.fromString("9af8d19b-c53a-41dc-b43e-251d77f4fc2b");
  private static final UUID CURATOR_IDENTIFIER =
          UUID.fromString("40c5ad0d-41f7-494b-8157-33fad16012aa");
  private static final UUID ADMIN_IDENTIFIER =
          UUID.fromString("0d2c04f1-e25f-41b5-b4cd-3566a081200f");
  private static final UUID ENCRYPT_UPGRADE_USER_IDENTIFIER =
          UUID.fromString("a7365322-0aac-4602-83b6-380bccb786e2");

  private static final UUID BOOK_CLEAN_CODE_IDENTIFIER =
          UUID.fromString("f9bf70d6-e56d-4cab-be6b-294cd05f599f");
  private static final UUID BOOK_CLOUD_NATIVE_IDENTIFIER =
          UUID.fromString("3038627d-627e-448d-8422-0a5705c9e8f1");
  private static final UUID BOOK_SPRING_ACTION_IDENTIFIER =
          UUID.fromString("081314cb-4abf-43e5-9b38-7d7261edb10d");
  private static final UUID BOOK_DEVOPS_IDENTIFIER =
          UUID.fromString("02c3d1fb-ca32-46bd-818f-704012b3fe9c");

  private final BookRepository bookRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public DataInitializer(
          BookRepository bookRepository,
          UserRepository userRepository,
          PasswordEncoder passwordEncoder) {
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) throws InterruptedException {
//    createUsers();
//    createBooks();
//    DelegatingPasswordEncoder oldPasswordEncoder =
//            new DelegatingPasswordEncoder(
//                    "MD5", Collections.singletonMap("MD5", new MessageDigestPasswordEncoder("MD5")));
//
    userRepository.findAll()
            .publishOn(Schedulers.boundedElastic())
            .doOnNext(user -> {
              String encodedPassword = passwordEncoder.encode(user.getLastName().toLowerCase());
              user.setPassword(encodedPassword);
              userRepository.save(user).subscribe();
            }).subscribe();


//    System.out.println(passwordEncoder.encode("old@example.com"));
//    System.out.println(oldPasswordEncoder.encode("user"));
  }

  private void createUsers() {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    DelegatingPasswordEncoder oldPasswordEncoder =
            new DelegatingPasswordEncoder(
                    "MD5", Collections.singletonMap("MD5", new MessageDigestPasswordEncoder("MD5")));

    logger.info("Creating users with LIBRARY_USER, LIBRARY_CURATOR and LIBRARY_ADMIN roles...");
    userRepository
            .saveAll(
                    Flux.just(
                            new Users(
                                    USER_WAYNE_IDENTIFIER,
                                    "bruce.wayne@example.com",
                                    passwordEncoder.encode("wayne"),
                                    "Bruce",
                                    "Wayne",
                                    Collections.singletonList(Role.LIBRARY_USER),
                                    "ME",
                                    "THIRD_YEAR"
                            ),
                            new Users(
                                    USER_BANNER_IDENTIFIER,
                                    "bruce.banner@example.com",
                                    passwordEncoder.encode("banner"),
                                    "Bruce",
                                    "Banner",
                                    Collections.singletonList(Role.LIBRARY_USER),
                                    "CS",
                                    "FIRST_YEAR"),
                            new Users(
                                    CURATOR_IDENTIFIER,
                                    "peter.parker@example.com",
                                    passwordEncoder.encode("parker"),
                                    "Peter",
                                    "Parker",
                                    Collections.singletonList(Role.LIBRARY_CURATOR),
                                    "IT",
                                    "FOURTH_YEAR"),
                            new Users(
                                    ADMIN_IDENTIFIER,
                                    "clark.kent@example.com",
                                    passwordEncoder.encode("kent"),
                                    "Clark",
                                    "Kent",
                                    Collections.singletonList(Role.LIBRARY_ADMIN),
                                    "ETC",
                                    "FIRST_YEAR"),
                            new Users(
                                    ENCRYPT_UPGRADE_USER_IDENTIFIER,
                                    "old@example.com",
                                    oldPasswordEncoder.encode("user"),
                                    "Library",
                                    "OldEncryption",
                                    Collections.singletonList(Role.LIBRARY_USER),
                                    "CE",
                                    "SECOND_YEAR")))
            .log()
            .then(userRepository.count())
            .subscribe(c -> logger.info("{} users created", c));
  }

  private void createBooks() {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    logger.info("Creating some books...");

    userRepository
            .findOneByEmail("bruce.wayne@example.com")
            .single()
            .flatMap(
                    user ->
                            Flux.just(
                                            new Book(
                                                    BOOK_CLEAN_CODE_IDENTIFIER,
                                                    "9780132350884",
                                                    "Clean Code",
                                                    "Even bad code can function. But if code isn’t clean, it can bring a development "
                                                            + "organization to its knees. Every year, countless hours and significant resources are "
                                                            + "lost because of poorly written code. But it doesn’t have to be that way. "
                                                            + "Noted software expert Robert C. Martin presents a revolutionary paradigm with Clean Code: "
                                                            + "A Handbook of Agile Software Craftsmanship . Martin has teamed up with his colleagues from "
                                                            + "Object Mentor to distill their best agile practice of cleaning code “on the fly” into a book "
                                                            + "that will instill within you the values of a software craftsman and make you a better "
                                                            + "programmer—but only if you work at it.",
                                                    Collections.singletonList("Bob C. Martin"),
                                                    true,
                                                    user.getId()),
                                            new Book(
                                                    BOOK_CLOUD_NATIVE_IDENTIFIER,
                                                    "9781449374648",
                                                    "Cloud Native Java",
                                                    "What separates the traditional enterprise from the likes of Amazon, Netflix, "
                                                            + "and Etsy? Those companies have refined the art of cloud native development to "
                                                            + "maintain their competitive edge and stay well ahead of the competition. "
                                                            + "This practical guide shows Java/JVM developers how to build better software, "
                                                            + "faster, using Spring Boot, Spring Cloud, and Cloud Foundry.",
                                                    Arrays.asList("Josh Long", "Kenny Bastiani"),
                                                    false,
                                                    null),
                                            new Book(
                                                    BOOK_SPRING_ACTION_IDENTIFIER,
                                                    "9781617291203",
                                                    "Spring in Action: Covers Spring 4",
                                                    "Spring in Action, Fourth Edition is a hands-on guide to the Spring Framework, "
                                                            + "updated for version 4. It covers the latest features, tools, and practices "
                                                            + "including Spring MVC, REST, Security, Web Flow, and more. You'll move between "
                                                            + "short snippets and an ongoing example as you learn to build simple and efficient "
                                                            + "J2EE applications. Author Craig Walls has a special knack for crisp and "
                                                            + "entertaining examples that zoom in on the features and techniques you really need.",
                                                    Collections.singletonList("Craig Walls"),
                                                    false,
                                                    null),
                                            new Book(
                                                    BOOK_DEVOPS_IDENTIFIER,
                                                    "9781942788003",
                                                    "The DevOps Handbook",
                                                    "Wondering if The DevOps Handbook is for you? Authors, Gene Kim, Jez Humble, "
                                                            + "Patrick Debois and John Willis developed this book for anyone looking to transform "
                                                            + "their IT organization—especially those who want to make serious changes through the "
                                                            + "DevOps methodology to increase productivity, profitability and win the marketplace.",
                                                    Arrays.asList("Gene Kim", "Jez Humble", "Patrick Debois"),
                                                    false,
                                                    null))
                                    .flatMap(bookRepository::save)
                                    .then(bookRepository.count()))
            .subscribe(
                    c -> logger.info("{} books created", c), t -> logger.error("Error {}", t.getMessage()));
  }
}
