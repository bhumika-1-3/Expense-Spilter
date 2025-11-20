package com.example.Splitter.Controller;


import com.example.Splitter.Entity.AppGroup;
import com.example.Splitter.Model.CreateGroupRequest;
import com.example.Splitter.Model.GroupAndUserResponse;
import com.example.Splitter.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public String createGroup(@RequestBody CreateGroupRequest info){
        try{
            return groupService.createGroup(info);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("id/{id}")
    public GroupAndUserResponse getGroupdata(@PathVariable String id){
        try{
            return groupService.getGroupData(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public List<GroupAndUserResponse> getAllGroups(){
        try{
            return groupService.getAllGroups();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
