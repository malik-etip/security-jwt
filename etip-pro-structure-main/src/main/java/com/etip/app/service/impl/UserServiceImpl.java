package com.etip.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etip.app.entity.User;
import com.etip.app.repository.UserRepository;
import com.etip.app.serivice.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> getUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}

}
