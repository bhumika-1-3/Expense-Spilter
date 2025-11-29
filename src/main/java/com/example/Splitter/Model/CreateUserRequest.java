package com.example.Splitter.Model;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String password;
    private String email;
}
