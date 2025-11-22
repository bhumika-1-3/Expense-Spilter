package com.example.Splitter.Model;

import com.example.Splitter.Entity.AppUser;
import com.example.Splitter.Enum.EditingType;
import lombok.Getter;

import java.util.List;

@Getter
public class EditUsersInGroup {
    private String id;
    private List<String> users;
    private EditingType type;
}
