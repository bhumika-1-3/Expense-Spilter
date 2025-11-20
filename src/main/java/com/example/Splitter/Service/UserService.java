package com.example.Splitter.Service;

import com.example.Splitter.Entity.AppGroup;
import com.example.Splitter.Entity.AppUser;
import com.example.Splitter.Model.CreateUserRequest;
import com.example.Splitter.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public String createUser(CreateUserRequest info){
        try{
            AppUser appUser = new AppUser();
            appUser.setEmail(info.getEmail());
            appUser.setName(info.getName());
            userRepo.save(appUser);
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
