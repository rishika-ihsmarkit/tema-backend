package com.osttra.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.osttra.entity.User;
import com.osttra.repository.UserRepository;
import com.osttra.to.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("inside CustomUserDetailsService");
		Optional<User> optional = userRepository.findById(username);
				
				User user = null;
				
				if(optional.isPresent()) {
					
					user = optional.get();
				}
				
				
				return new CustomUserDetails(user);
			}
				
		
	

}
