package com.example.Splitter.Repo;

import com.example.Splitter.Model.Group;
import org.springframework.data.jpa.repository.JpaRepository;


//your entity and idtype
public interface GroupRepo extends JpaRepository<Group,String> {
}
