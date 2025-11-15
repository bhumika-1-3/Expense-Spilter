package com.example.Splitter.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class AppGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String groupId;
    private String groupName;
    @ManyToMany
    @JoinTable(
            name = "group_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUser> users;
}
