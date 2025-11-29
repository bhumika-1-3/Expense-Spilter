package com.example.Splitter.Controller;


import com.example.Splitter.Config.JwtUtil;
import com.example.Splitter.Config.OverRideUserDetailService;
import com.example.Splitter.Entity.AppUser;
import com.example.Splitter.Model.CreateUserRequest;
import com.example.Splitter.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OverRideUserDetailService overRideUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public String createUser(@RequestBody CreateUserRequest info){
        try{
            log.info("user controller post {}",info);
            return userService.createUser(info);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody CreateUserRequest info){
        try{
// Check username + password
//Get user info from DB
//Make a JWT token
//Send the token back

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(info.getName(),info.getPassword()));
            log.info("user controller login {}",info);
            UserDetails userDetails = overRideUserDetailService.loadUserByUsername(info.getName());
            String jwt = jwtUtil.generateToken(userDetails);
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
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
