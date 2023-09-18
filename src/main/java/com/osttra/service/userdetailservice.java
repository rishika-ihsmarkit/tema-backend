package com.osttra.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osttra.entity.User;
import com.osttra.repository.UserRepository;

@Service
public class userdetailservice {

	@Autowired
	UserRepository userRepository;

	
	

	public User saveUser(User user) {
        return userRepository.save(user);
    }
	
	public List<User> getAllUser() {
        return userRepository.findAll();
    }
	
	public User getUserById(String username) {
        return userRepository.findById(username).orElse(null);
    }

	@Transactional
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

}
