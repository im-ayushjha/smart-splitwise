package com.ayush.smart_splitwise.core.group.repository;

import com.ayush.smart_splitwise.core.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface GroupRepository extends JpaRepository<Group, Long> {

}
