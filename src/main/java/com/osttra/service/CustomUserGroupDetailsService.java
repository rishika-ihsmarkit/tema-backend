package com.osttra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osttra.entity.UserGroup;
import com.osttra.repository.userGroupRepository;



@Service
public class CustomUserGroupDetailsService {
	
	@Autowired
	userGroupRepository  userGroupRepository;

	public UserGroup saveUserGroup(UserGroup userGroup) {
		return userGroupRepository.save(userGroup);
	}
	
	public List<UserGroup> getAllUserGroups() {
        return userGroupRepository.findAll();
    }
	
	public UserGroup getUserGroupById(Long userId) {
        return userGroupRepository.findById(userId).orElse(null);
    }
}