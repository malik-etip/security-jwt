package com.etip.app.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.etip.app.entity.Role;
import com.etip.app.entity.User;
import com.etip.app.model.request.LoginRequest;
import com.etip.app.model.request.SignupRequest;
import com.etip.app.model.response.LoginResponse;
import com.etip.app.repository.RoleRepository;
import com.etip.app.repository.UserRepository;
import com.etip.app.security.jwt.JwtUtils;
import com.etip.app.security.services.UserDetailsImpl;
import com.etip.app.serivice.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ResponseEntity<String> addUser(SignupRequest signupRequest) {
		
		try {
			User user = new User();
			
			user.setEmail(signupRequest.getEmail());
			user.setPassword(encoder.encode(signupRequest.getPassword()));
			user.setUserName(signupRequest.getUserName());
			user.setIpAddress(signupRequest.getIpAddress());
			Set<Role> roles = new HashSet<>();
			
			for(String role: signupRequest.getRoles()) {
				Role roleObj = roleRepository.findByRoleName(role);
				
				if(roleObj!=null) {
					roles.add(roleObj);
				}
			}
			
			user.setRoles(roles);
			
			userRepository.save(user);
			
			return ResponseEntity.ok("User added successfully!");
			
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ResponseEntity<Object> login(LoginRequest loginRequest, String ipAddress) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		UserDetailsImpl userDetails= (UserDetailsImpl) authenticate.getPrincipal();
		String token = jwtUtils.generateJwtToken(authenticate);
		return ResponseEntity.ok(new LoginResponse(userDetails.getEmail(),userDetails.getUsername(),token));
	}

}
