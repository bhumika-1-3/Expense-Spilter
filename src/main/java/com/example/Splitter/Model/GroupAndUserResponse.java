package com.example.Splitter.Model;

import lombok.Data;

import java.util.List;

@Data
public class GroupAndUserResponse {
    private String groupName;
    private List<AppUser> users;
}
