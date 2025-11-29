package com.example.Splitter.Repo;

import com.example.Splitter.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


//your entity and idtype
public interface UserRepo extends JpaRepository<AppUser,String> {
    Optional<AppUser> findByname(String name);
}
