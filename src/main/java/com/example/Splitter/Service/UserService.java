package com.example.Splitter.Service;

import com.example.Splitter.Entity.AppGroup;
import com.example.Splitter.Entity.AppUser;
import com.example.Splitter.Model.CreateUserRequest;
import com.example.Splitter.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> createUser(CreateUserRequest info){
        try{
            if(userRepo.existsByname(info.getName())){
                return new ResponseEntity<>("User already exists with this name", HttpStatus.BAD_REQUEST);
            }
            AppUser appUser = new AppUser();
            appUser.setEmail(info.getEmail());
            appUser.setName(info.getName());
            appUser.setPassword(passwordEncoder.encode(info.getPassword()));
            userRepo.save(appUser);
            return new ResponseEntity<>("User created", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<AppUser> getUsers(){
        try{
            return userRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public String deleteUser(String id){
        try{
            AppUser user = userRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            for (AppGroup group : user.getGroups()) {
                group.getUsers().remove(user);   // remove user from each group
            }
            userRepo.delete(user);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
