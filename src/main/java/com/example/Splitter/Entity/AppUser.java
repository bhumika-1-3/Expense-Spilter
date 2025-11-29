package com.example.Splitter.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    private String name;
    private String email;
    private String password;
    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private List<AppGroup> groups;
//
//    private List<Map<String,Double>> owedByYou;
//    private List<Map<String,Double>> owedToYou;
}
