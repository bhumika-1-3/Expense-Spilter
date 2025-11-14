package com.example.Splitter.Controller;


import com.example.Splitter.Model.Group;
import com.example.Splitter.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public String createGroup(@RequestBody Group info){
        try{
            return groupService.createGroup(info);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
