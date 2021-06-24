package com.etip.app.serivice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.etip.app.model.request.LoginRequest;
import com.etip.app.model.request.SignupRequest;

@Component
public interface AuthService {

	public ResponseEntity<String> addUser(SignupRequest signupRequest);
	
	public ResponseEntity<Object> login(LoginRequest loginRequest);
}
