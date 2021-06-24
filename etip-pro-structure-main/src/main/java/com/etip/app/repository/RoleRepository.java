package com.etip.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etip.app.entity.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role,String> {
	 
	Role findByRoleName(String role);
}