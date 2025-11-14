package com.example.Splitter.Controller;


import com.example.Splitter.Model.AppUser;
import com.example.Splitter.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public String createUser(@RequestBody AppUser info){
        try{
            log.info("user controller post {}",info);
            return userService.createUser(info);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public List<AppUser> getUsers(){
        try{
            log.info("user controller get");
            return userService.getUsers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping
    public String deleteUser(@RequestBody String id){
        try{
            log.info("user controller delete {}",id);
            return userService.deleteUser(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
