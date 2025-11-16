package com.example.Splitter.Service;

import com.example.Splitter.Model.AppGroup;
import com.example.Splitter.Model.AppUser;
import com.example.Splitter.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
