package com.ayush.smart_splitwise.core.group.sevice;

import com.ayush.smart_splitwise.common.exception.custom.IllegalOperationException;
import com.ayush.smart_splitwise.common.exception.custom.MemberAlreadyExistsException;
import com.ayush.smart_splitwise.common.exception.custom.ResourceNotFoundException;
import com.ayush.smart_splitwise.common.util.AuthenticationService;
import com.ayush.smart_splitwise.core.group.dto.*;
import com.ayush.smart_splitwise.core.group.entity.Group;
import com.ayush.smart_splitwise.core.group.entity.GroupMember;
import com.ayush.smart_splitwise.core.group.repository.GroupMemberRepository;
import com.ayush.smart_splitwise.core.group.repository.GroupRepository;
import com.ayush.smart_splitwise.core.user.entity.User;
import com.ayush.smart_splitwise.core.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    private final GroupMemberRepository groupMemberRepository;

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;

    @Transactional
    public GroupResponse createGroup(CreateGroupRequest request) {
        User user=authenticationService.getCurrentUser();
        Group group=Group.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Group savedGroup=groupRepository.save(group);
        GroupMember groupMember= GroupMember.builder()
                .group(savedGroup)
                .user(user)
                .joinedAt(LocalDateTime.now())
                .build();
        groupMemberRepository.save(groupMember);
        return new GroupResponse(savedGroup.getId(),"Group created Successfully");
    }

    public GroupDetailsResponse getGroupById(Long groupId) {
        Group group = validateGroup(groupId);
        User currentUser=authenticationService.getCurrentUser();
        validateGroupMember(groupId, currentUser.getId());
        User createdBy = group.getCreatedBy();
        UserSummary userSummary = new UserSummary(
                createdBy.getId(),
                createdBy.getFirstName() + " " + createdBy.getLastName()
        );
        List<GroupMember> groupMembers=groupMemberRepository.findByGroupId(groupId);
        List<MemberResponse> memberResponses=new ArrayList<>();
        for(GroupMember groupMember:groupMembers){
            User cur=groupMember.getUser();
            MemberResponse memberResponse=MemberResponse.builder()
                    .id(cur.getId())
                    .firstName(cur.getFirstName())
                    .lastName(cur.getLastName())
                    .email(cur.getEmail())
                    .phoneNumber(cur.getPhoneNumber())
                    .build();
            memberResponses.add(memberResponse);
        }
        GroupDetailsResponse response=GroupDetailsResponse.builder()
                .id(groupId)
                .name(group.getName())
                .description(group.getDescription())
                .createdBy(userSummary)
                .members(memberResponses)
                .createdAt(group.getCreatedAt())
                .updatedAt(group.getUpdatedAt())
                .build();
        return response;
    }

    public List<GroupSummaryResponse> getAllGroups() {
        User user=authenticationService.getCurrentUser();
        List<GroupMember> groupMembers=groupMemberRepository.findByUserId(user.getId());
        List<GroupSummaryResponse> groupResponses=new ArrayList<>();
        for(GroupMember groupMember:groupMembers){
            Group group = groupMember.getGroup();
            User createdBy=group.getCreatedBy();
            Long memberCount=groupMemberRepository.countByGroupId(group.getId());
            UserSummary userSummary=new UserSummary(createdBy.getId(), createdBy.getFirstName()+" "+createdBy.getLastName());
            GroupSummaryResponse groupSummaryResponse=GroupSummaryResponse.builder()
                    .id(group.getId())
                    .name(group.getName())
                    .description(group.getDescription())
                    .createdBy(userSummary)
                    .memberCount(memberCount)
                    .createdAt(group.getCreatedAt())
                    .build();
            groupResponses.add(groupSummaryResponse);
        }
        return groupResponses;
    }

    @Transactional
    public GroupResponse addMember(Long groupId, AddMemberRequest addMemberRequest) {
        Group group = validateGroup(groupId);
        User currentUser =authenticationService.getCurrentUser();
        validateGroupMember(groupId, currentUser.getId());
        User user = userRepository.findByEmail(addMemberRequest.getEmail()).orElseThrow(() -> new ResourceNotFoundException("No user found with provided email"));
        boolean alreadyPresentGroupMember= groupMemberRepository.findByGroupIdAndUserId(groupId, user.getId()).isPresent();
        if(alreadyPresentGroupMember)
            throw new MemberAlreadyExistsException(
                    "User is already a member of this group");
        GroupMember groupMember=GroupMember.builder()
                .group(group)
                .user(user)
                .joinedAt(LocalDateTime.now())
                .build();
        group.setUpdatedAt(LocalDateTime.now());
        groupMemberRepository.save(groupMember);
        return new GroupResponse(group.getId(),"Member added successfully to the group.");
    }

    private void validateGroupMember(Long groupId, Long userId) {
        groupMemberRepository.findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() ->
                        new AccessDeniedException("You are not a member of this group"));
    }

    private Group validateGroup(Long groupId) {
        Group group= groupRepository.findById(groupId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Group not found with id: "+groupId));
        return group;
    }

}
