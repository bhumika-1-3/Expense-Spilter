package com.example.Splitter.Service;

import com.example.Splitter.Model.Group;
import com.example.Splitter.Repo.GroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private GroupRepo groupRepo;

    public String createGroup(Group info){
        try{
            groupRepo.save(info);
            return info.getGroupName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
