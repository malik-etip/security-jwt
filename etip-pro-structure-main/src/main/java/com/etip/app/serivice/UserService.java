package com.etip.app.serivice;

import java.util.List;

import org.springframework.stereotype.Component;

import com.etip.app.entity.User;

@Component
public interface UserService {
	
	public List<User> getUsers();

}
