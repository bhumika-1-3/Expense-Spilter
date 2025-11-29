package com.example.Splitter.Repo;

import com.example.Splitter.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepo extends JpaRepository<RefreshToken,String> {
    boolean existsBytoken(String token);
    RefreshToken findBytoken(String token);
}
