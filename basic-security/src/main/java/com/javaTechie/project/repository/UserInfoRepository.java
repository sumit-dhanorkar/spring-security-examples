package com.javaTechie.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaTechie.project.entity.UserInfo;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);

}