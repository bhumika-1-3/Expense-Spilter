package com.example.Splitter.Repo;

import com.example.Splitter.Entity.AppGroup;
import org.springframework.data.jpa.repository.JpaRepository;


//your entity and idtype
public interface GroupRepo extends JpaRepository<AppGroup,String> {
}
