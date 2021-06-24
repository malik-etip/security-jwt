package com.etip.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etip.app.entity.Role;
import com.etip.app.model.request.LoginRequest;
import com.etip.app.model.request.SignupRequest;
import com.etip.app.repository.RoleRepository;
import com.etip.app.repository.UserRepository;
import com.etip.app.serivice.AuthService;

@CrossOrigin("*")
@RequestMapping("/api/auth")
@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@PostMapping(value="/add_user")
	public ResponseEntity<String> addUser(HttpServletRequest request,@RequestBody @Valid SignupRequest signupRequest) {
		System.out.println("payload : "+signupRequest);
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		if (ipAddress == null) {  
		    ipAddress = request.getRemoteAddr();  
		}
		signupRequest.setIpAddress(ipAddress);
		System.out.println("payload : "+signupRequest);
		return authService.addUser(signupRequest);
	}
	
	
	@PostMapping(value="/signin")
	public ResponseEntity<Object> login(HttpServletRequest request,@RequestBody @Valid LoginRequest loginRequest) {
		
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		if (ipAddress == null) {  
		    ipAddress = request.getRemoteAddr();  
		}
		
		return authService.login(loginRequest);
	}
	
	
	
	
	
	
	@GetMapping("/roles")
	public List<Role> getRoles() {
		List<Role> findAll = roleRepository.findAll();
		return findAll;
	}
	
	
}
