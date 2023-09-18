package com.osttra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osttra.entity.UserGroup;

@Repository
public interface userGroupRepository extends JpaRepository<UserGroup, Long>{

}
