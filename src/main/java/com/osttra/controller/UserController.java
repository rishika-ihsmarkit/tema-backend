package com.osttra.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.osttra.helper.JWTHelper;
import com.osttra.service.CustomUserDetailsService;
import com.osttra.service.userdetailservice;
import com.osttra.to.CustomResponse;
import com.osttra.to.JWTRequest;

@RestController
public class UserController {

	@Autowired
	userdetailservice userDetailsService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	JWTHelper jwtHelper;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;


	@PostMapping("/token")
	public String generateToken(@RequestBody JWTRequest jwtRequest) {

		System.out.println("JWT request is " + jwtRequest);

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();

		}

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String jwtToken = jwtHelper.generateToken(userDetails);
		System.out.println("JWT Token is " + jwtToken);

		return jwtToken;

	}
	
	//////////////////////////////////////// ADD USER //////////////////////////////////////////////////////////////////////////
	

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/registeruser")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		try {
			
			User existingUser = userDetailsService.getUserById(user.getUsername());

			if (existingUser == null) {
				
				String password = user.getPassword();
				String encodedPassword = this.bCryptPasswordEncoder.encode(password);
				user.setPassword(encodedPassword);

				User savedUser = userDetailsService.saveUser(user);

				CustomResponse<User> successResponse = new CustomResponse<>(savedUser, "User registered successfully", 200);
		        return new ResponseEntity<>(successResponse, HttpStatus.OK);
	            
			}
			
			CustomResponse<String> errorResponse = new CustomResponse<>("", "Duplicate user", 409);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			
			
		} catch (IllegalArgumentException e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Bad Request: " + e.getMessage(), 400);
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	        
	    } catch (Exception e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Internal Server Error", 500);
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	        
	    }
	}
	
	//////////////////////////////////////// LIST ALL USERS //////////////////////////////////////////////////////////////////////////

	@CrossOrigin(origins = "http://localhost:3000")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/all")
	@ResponseBody
	public ResponseEntity<?> getAllUser() {
		try{
			
			List<User> users = userDetailsService.getAllUser();

			CustomResponse<List<User>> successResponse = new CustomResponse<>(users, "Listed all users", 200);
	        return new ResponseEntity<>(successResponse, HttpStatus.OK);
			
		} catch (IllegalArgumentException e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Bad Request: " + e.getMessage(), 400);
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	        
	    } catch (Exception e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Internal Server Error", 500);
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	        
	    }
	}
	
	//////////////////////////////////////// FIND USER GROUPS //////////////////////////////////////////////////////////////////////////

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{username}/groups")
	@ResponseBody
	public ResponseEntity<?> getUserGroups(@PathVariable String username) {
		
		try {
			
			User user = userDetailsService.getUserById(username);

			if (user == null) {
				
				CustomResponse<String> errorResponse = new CustomResponse<>("", "User not found", 404);
	            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	            
			}

			Set<UserGroup> userGroups = user.getUserGroups();
			
			CustomResponse<Set<UserGroup>> successResponse = new CustomResponse<>(userGroups, "User groups displayed succesfully", 200);
	        return new ResponseEntity<>(successResponse, HttpStatus.OK);
			
		} catch (IllegalArgumentException e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Bad Request: " + e.getMessage(), 400);
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	        
	    } catch (Exception e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Internal Server Error", 500);
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	        
	    }

	}
	
	//////////////////////////////////////// FIND USER //////////////////////////////////////////////////////////////////////////

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("finduser/{username}")
	@ResponseBody
	public ResponseEntity<?> getSpecificUser(@PathVariable String username) {
	    try {
	        User user = userDetailsService.getUserById(username);

	        if (user == null) {
	            CustomResponse<String> errorResponse = new CustomResponse<>("", "User not found", 404);
	            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	        }

	        CustomResponse<User> successResponse = new CustomResponse<>(user, "User found", 200);
	        return new ResponseEntity<>(successResponse, HttpStatus.OK);
	        
	    } catch (IllegalArgumentException e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Bad Request: " + e.getMessage(), 400);
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	        
	    } catch (Exception e) {

	        CustomResponse<String> errorResponse = new CustomResponse<>("", "Internal Server Error", 500);
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	        
	    }
	}


	//////////////////////////////////////// UPDATE USER //////////////////////////////////////////////////////////////////////////
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{username}")
	public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User updatedUser) {
		
		try {
			
			User existingUser = userDetailsService.getUserById(username);

			if (existingUser == null) {

				CustomResponse<String> errorResponse = new CustomResponse<>("", "User not found", 404);
	            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
			}
			
			existingUser.setFirstName(updatedUser.getFirstName());
			existingUser.setLastName(updatedUser.getLastName());
			existingUser.setEmail(updatedUser.getEmail());
			existingUser.setRole(updatedUser.getRole());
			existingUser.setUserGroups(updatedUser.getUserGroups());

			CustomResponse<User> successResponse = new CustomResponse<User>(userDetailsService.saveUser(existingUser), "User updated successfully", 200);
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
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
		
		try {
			
			User userToDelete = userDetailsService.getUserById(username);

	        if (userToDelete == null) {
	            CustomResponse<String> errorResponse = new CustomResponse<>("", "User not found", 404);
	            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	        }

	        userDetailsService.deleteUser(username);

	        CustomResponse<User> successResponse = new CustomResponse<User>(userToDelete, "User deleted successfully", 200);
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
