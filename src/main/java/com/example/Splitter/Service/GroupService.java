package com.example.Splitter.Service;

import com.example.Splitter.Model.AppGroup;
import com.example.Splitter.Model.AppUser;
import com.example.Splitter.Model.GroupAndUserResponse;
import com.example.Splitter.Repo.GroupRepo;
import com.example.Splitter.Repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GroupService {

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private UserRepo userRepo;

    public String createGroup(AppGroup info){
        try{
            List<AppUser> usersInGroup = info.getUsers();
            for(AppUser user : usersInGroup){
//                todo : create new user
                if (!userRepo.existsById(user.getUserId())){
                    log.info("User doesnot exist");
                    throw new RuntimeException();
                }
            }
            groupRepo.save(info);
            return info.getGroupId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GroupAndUserResponse getGroupData(String id){
        try{
            Optional<AppGroup> groupOpt = groupRepo.findById(id);
            GroupAndUserResponse response = new GroupAndUserResponse();
            if (groupOpt.isPresent()){
                AppGroup group = groupOpt.get();
                response.setGroupName(group.getGroupName());
                response.setUsers(group.getUsers());  // this is List<AppUser>
            }
            return response;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<GroupAndUserResponse> getAllGroups(){
        try{
            List<AppGroup> groups = groupRepo.findAll();
            List<GroupAndUserResponse> data = new ArrayList<>();
            for(AppGroup group : groups){
                GroupAndUserResponse response = getGroupData(group.getGroupId());
                data.add(response);
            }
            return data;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
