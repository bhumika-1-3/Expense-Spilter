package com.example.Splitter.Repo;

import com.example.Splitter.Entity.AppUser;
import com.example.Splitter.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshRepo extends JpaRepository<RefreshToken,String> {
    boolean existsBytoken(String token);
    RefreshToken findBytoken(String token);
    Optional<RefreshToken> findByUserUserId(String id);
}
