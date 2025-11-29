package com.example.Splitter.Controller;


import com.example.Splitter.Config.JwtUtil;
import com.example.Splitter.Config.OverRideUserDetailService;
import com.example.Splitter.Entity.AppUser;
import com.example.Splitter.Entity.RefreshToken;
import com.example.Splitter.Model.CreateUserRequest;
import com.example.Splitter.Repo.RefreshRepo;
import com.example.Splitter.Repo.UserRepo;
import com.example.Splitter.Service.RefreshTokenService;
import com.example.Splitter.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OverRideUserDetailService overRideUserDetailService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshRepo refreshRepo;

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest info){
        try{
            log.info("user controller post {}",info);
            return userService.createUser(info);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody CreateUserRequest info){
        try{
// Check username + password
//Get user info from DB
//Make a JWT token
//Send the token back

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(info.getName(),info.getPassword()));
            log.info("user controller login {}",info);
            UserDetails userDetails = overRideUserDetailService.loadUserByUsername(info.getName());
            String jwt = jwtUtil.generateToken(userDetails);
            String username = userDetails.getUsername();
            AppUser user = userRepo.findByname(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String refreshToken = refreshTokenService.createRefreshToken(user);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken",jwt);
            tokens.put("refreshToken",refreshToken);
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody String requestRefreshToken) {

        if(refreshRepo.existsBytoken(requestRefreshToken)){
            RefreshToken refreshToken = refreshRepo.findBytoken(requestRefreshToken);
            if(refreshTokenService.isValid(refreshToken)){
                UserDetails userDetails = overRideUserDetailService.loadUserByUsername(
                        refreshToken.getUser().getName()
                );

                String newAccessToken = jwtUtil.generateToken(userDetails);
                return new ResponseEntity<>(newAccessToken,HttpStatus.OK);
            }
            else{
                refreshRepo.delete(refreshToken);
                return new ResponseEntity<>("Token expired",HttpStatus.FORBIDDEN);
            }
        }
        else{
            return new ResponseEntity<>("Token does not exist",HttpStatus.NOT_FOUND);
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
