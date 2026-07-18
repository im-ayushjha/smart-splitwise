package com.ayush.smart_splitwise.core.group.controller;

import com.ayush.smart_splitwise.core.group.dto.*;
import com.ayush.smart_splitwise.core.group.sevice.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody CreateGroupRequest request){
        GroupResponse response = groupService.createGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDetailsResponse> getGroupById(@PathVariable Long groupId){
        GroupDetailsResponse response=groupService.getGroupById(groupId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GroupSummaryResponse>> getAllGroup(){
        List<GroupSummaryResponse> response = groupService.getAllGroups();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<GroupResponse> addMember(@PathVariable Long groupId, @Valid @RequestBody AddMemberRequest addMemberRequest){
        return ResponseEntity.
                status(HttpStatus.CREATED).body(groupService.addMember(groupId, addMemberRequest));
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupResponse> deleteMember(@PathVariable Long groupId, @PathVariable Long userId){
        return null;
    }
}
