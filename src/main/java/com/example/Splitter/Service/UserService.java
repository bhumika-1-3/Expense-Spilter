package com.example.Splitter.Service;

import com.example.Splitter.Model.AppUser;
import com.example.Splitter.Model.Group;
import com.example.Splitter.Repo.GroupRepo;
import com.example.Splitter.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public String createUser(AppUser info){
        try{
            userRepo.save(info);
            return info.getName();
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

    public String deleteUser(String id){
        try{
            userRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
