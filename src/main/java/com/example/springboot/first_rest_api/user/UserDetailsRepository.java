package com.example.springboot.first_rest_api.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {
    List<UserDetailsEntity> findByRole(String role);
}
