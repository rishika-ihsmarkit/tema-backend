package com.osttra.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osttra.entity.UserGroup;
import com.osttra.repository.userGroupRepository;

@Service
public class usergroupdetailservice {

	@Autowired
	userGroupRepository userGroupRepository;

	public UserGroup saveUserGroup(UserGroup userGroup) {
        return userGroupRepository.save(userGroup);
    }
	
	public List<UserGroup> getAllUserGroups() {
        return userGroupRepository.findAll();
    }
	
	public UserGroup getUserGroupById(String usergroupid) {
        return userGroupRepository.findById(usergroupid).orElse(null);
    }

	@Transactional
    public void deleteUserGroup(String usergroupid) {
		userGroupRepository.deleteById(usergroupid);
    }

}