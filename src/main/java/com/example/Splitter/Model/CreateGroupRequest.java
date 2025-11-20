package com.example.Splitter.Model;

import com.example.Splitter.Entity.AppUser;
import lombok.Data;

import java.util.List;

@Data
public class CreateGroupRequest {
    private String groupName;
    private List<AppUser> users;
}
