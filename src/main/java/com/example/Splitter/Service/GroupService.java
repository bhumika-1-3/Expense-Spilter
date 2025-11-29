package com.example.Splitter.Service;

import com.example.Splitter.Entity.AppGroup;
import com.example.Splitter.Entity.AppUser;
import com.example.Splitter.Enum.EditingType;
import com.example.Splitter.Model.CreateGroupRequest;
import com.example.Splitter.Model.EditUsersInGroup;
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

    public String createGroup(CreateGroupRequest info){
        try{
            List<String> usersInGroup = info.getUsers();
            List<AppUser> users = new ArrayList<>();
            for(String id : usersInGroup){
                AppUser user = userRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                users.add(user);
            }

            AppGroup appGroup = new AppGroup();
            appGroup.setGroupName(info.getGroupName());
            appGroup.setUsers(users);
            groupRepo.save(appGroup);
            return appGroup.getGroupId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String editUsers(String id, List<String> userIds, EditingType type) {
        try {
            AppGroup group = groupRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Group not found"));

            List<AppUser> currentUsers = group.getUsers();

            List<AppUser> users = userRepo.findAllById(userIds);
            if (users.size() != userIds.size()) {
                throw new RuntimeException("Some users do not exist");
            }

            if (type == EditingType.ADD) {
                currentUsers.addAll(users);
            }
            else if (type == EditingType.DELETE) {
                currentUsers.removeAll(users);
            }

            groupRepo.save(group);
            return "Users edited";

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

    public String deleteGroup(String id){
        try{
            AppGroup group = groupRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Group not found"));
            groupRepo.delete(group);
            return "Group deleted";
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
