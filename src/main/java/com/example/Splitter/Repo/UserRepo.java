package com.example.Splitter.Repo;

import com.example.Splitter.Model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


//your entity and idtype
public interface UserRepo extends JpaRepository<AppUser,String> {
}
