package com.etip.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etip.app.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByUserName(String username);

}
