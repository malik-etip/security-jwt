package com.etip.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.etip.app.entity.User;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

	Optional<User> findByUserName(String username);

}
