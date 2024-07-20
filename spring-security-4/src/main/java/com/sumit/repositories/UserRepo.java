//package com.sumit.repositories;
//
//import com.sumit.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.Optional;
//
//public interface UserRepo extends JpaRepository<User,Integer> {
//
//    @Query("SELECT u FROM User u WHERE u.username=:username")
//    Optional<User> findUserByUsername(@Param("username") String username);
//}
