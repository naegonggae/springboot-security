package com.hospitalreview2.repository;

import com.hospitalreview2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName); // user 중복체크
}