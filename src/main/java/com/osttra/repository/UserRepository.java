package com.osttra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osttra.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
