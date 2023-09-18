package com.osttra.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.osttra.entity.User;
import com.osttra.entity.UserGroup;
import com.osttra.repository.userGroupRepository;
import com.osttra.service.CustomUserGroupDetailsService;
import com.osttra.service.userdetailservice;
import com.osttra.service.usergroupdetailservice;
import com.osttra.to.CustomResponse;

@RestController
public class UserGroupController {

	@Autowired
	CustomUserGroupDetailsService customUserGroupDetailsService;

	@Autowired
	usergroupdetailservice usergroupdetailservice;

	//////////////////////////////////////// ADD USER GROUP ///////////////////////////////////////////////////////////////

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/registerusergroup")
	public ResponseEntity<?> addUserGroup(@RequestBody UserGroup userGroup) {

		try {

			UserGroup savedUserGroup = usergroupdetailservice.saveUserGroup(userGroup);

			CustomResponse<UserGroup> successResponse = new CustomResponse<>(savedUserGroup, "User group added successfully", 200);
			return new ResponseEntity<>(successResponse, HttpStatus.OK);

		} catch (IllegalArgumentException e) {

			CustomResponse<String> errorResponse = new CustomResponse<>("", "Bad Request: " + e.getMessage(), 400);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

		} catch (Exception e) {

			CustomResponse<String> errorResponse = new CustomResponse<>("", "Internal Server Error", 500);
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	//////////////////////////////////////// LIST ALL USER GROUPS ////////////////////////////////////////////////////////////
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/allgroups", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> getAllUserGroups() {
		try{
			
			List<UserGroup> usergroups = usergroupdetailservice.getAllUserGroups();

			CustomResponse<List<UserGroup>> successResponse = new CustomResponse<>(usergroups, "Listed all user groups", 200);
	        return new ResponseEntity<>(successResponse, HttpStatus.OK);
			
		} catch (IllegalArgumentException e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Bad Request: " + e.getMessage(), 400);
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	        
	    } catch (Exception e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Internal Server Error", 500);
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	        
	    }
	}
	

	//////////////////////////////////////// FIND USERS OF GROUP //////////////////////////////////////////////////////////////////////////

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{usergroupid}/users")
	@ResponseBody
	public ResponseEntity<?> getUserGroups(@PathVariable Long usergroupid) {
		
		try {
			
			UserGroup userGroup = usergroupdetailservice.getUserGroupById(usergroupid);

			if (userGroup == null) {
				
				CustomResponse<String> errorResponse = new CustomResponse<>("", "User group not found", 404);
	            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	            
			}

			Set<User> users = userGroup.getUsers();
			
			CustomResponse<Set<User>> successResponse = new CustomResponse<>(users, "User groups displayed succesfully", 200);
	        return new ResponseEntity<>(successResponse, HttpStatus.OK);
			
		} catch (IllegalArgumentException e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Bad Request: " + e.getMessage(), 400);
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	        
	    } catch (Exception e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Internal Server Error", 500);
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	        
	    }

	}

	//////////////////////////////////////// FIND USER GROUP //////////////////////////////////////////////////////////////////////////

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("findusergroup/{usergroupid}")
	@ResponseBody
	public ResponseEntity<?> getSpecificUserGroup(@PathVariable Long usergroupid) {
	    try {
	    	
	        UserGroup userGroup = usergroupdetailservice.getUserGroupById(usergroupid);

	        if (userGroup == null) {
	        	
	            CustomResponse<String> errorResponse = new CustomResponse<>("", "User group not found", 404);
	            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	            
	        }

	        CustomResponse<UserGroup> successResponse = new CustomResponse<>(userGroup, "User group found", 200);
	        return new ResponseEntity<>(successResponse, HttpStatus.OK);
	        
	    } catch (IllegalArgumentException e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Bad Request: " + e.getMessage(), 400);
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	        
	    } catch (Exception e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Internal Server Error", 500);
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	        
	    }
	}
	

	//////////////////////////////////////// UPDATE USER GROUP //////////////////////////////////////////////////////////////////////////
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updategroup/{usergroupid}")
	public ResponseEntity<?> updateUserGroup(@PathVariable Long usergroupid, @RequestBody UserGroup updatedUserGroup) {
		
		try {
			
			System.out.println(updatedUserGroup.getUsers());
			
			UserGroup existingUserGroup = usergroupdetailservice.getUserGroupById(usergroupid);

			if (existingUserGroup == null) {

				CustomResponse<String> errorResponse = new CustomResponse<>("", "User group not found", 404);
	            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	            
			}
			
			existingUserGroup.setGroupname(updatedUserGroup.getGroupname());
			existingUserGroup.setDescription(updatedUserGroup.getDescription());
			existingUserGroup.setUsers(updatedUserGroup.getUsers());
			
			System.out.println(existingUserGroup.getUsers());

			CustomResponse<UserGroup> successResponse = new CustomResponse<>(usergroupdetailservice.saveUserGroup(existingUserGroup), "User group updated successfully", 200);
	        return new ResponseEntity<>(successResponse, HttpStatus.OK);
			
		} catch (IllegalArgumentException e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Bad Request: " + e.getMessage(), 400);
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	        
	    } catch (Exception e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Internal Server Error", 500);
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	        
	    }

	}
	
	//////////////////////////////////////// DELETE USER //////////////////////////////////////////////////////////////////////////

	
	@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletegroup/{usergroupid}")
    public ResponseEntity<?> deleteUserGroup(@PathVariable Long usergroupid) {
		
		try {
			
			UserGroup userGroupToDelete = usergroupdetailservice.getUserGroupById(usergroupid);

	        if (userGroupToDelete == null) {
	        	
	        	CustomResponse<String> errorResponse = new CustomResponse<>("", "User group not found", 404);
	            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	        }

	        usergroupdetailservice.deleteUserGroup(usergroupid);

	        CustomResponse<UserGroup> successResponse = new CustomResponse<UserGroup>(userGroupToDelete, "User deleted successfully", 200);
	        return new ResponseEntity<>(successResponse, HttpStatus.OK);
			
		} catch (IllegalArgumentException e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Bad Request: " + e.getMessage(), 400);
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	        
	    } catch (Exception e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Internal Server Error", 500);
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	        
	    }
     
    }
	
}
