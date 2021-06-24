package com.etip.app.model.request;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SignupRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank
	@Size(max = 20)
	private String userName;

	@NotBlank
	@Size(max = 50)
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;
	
	private String ipAddress;
	
	@NotEmpty
	private Set<String> roles = new HashSet<>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
