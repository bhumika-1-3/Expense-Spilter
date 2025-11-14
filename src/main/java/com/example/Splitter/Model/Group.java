package com.example.Splitter.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String groupId;
    private String groupName;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AppUser> users;
}
