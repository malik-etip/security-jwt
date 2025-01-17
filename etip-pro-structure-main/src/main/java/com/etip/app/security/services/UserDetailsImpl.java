package com.etip.app.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.etip.app.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	private String id;

	private String username;

	private String email;
	
	private String ipAddress;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(String id, String username, String email, String password, String ipAddress,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.ipAddress = ipAddress;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(), 
				user.getUserName(), 
				user.getEmail(),
				user.getPassword(),
				user.getIpAddress(),
				authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		System.out.println("UserDeatils service equals called : "+user);
		
		System.out.println("check equality:: "+Objects.equals(id, user.id) +  Objects.equals(ipAddress, user.getIpAddress()));
		return Objects.equals(id, user.id) && Objects.equals(ipAddress, user.getIpAddress());
	}

	@Override
	public String toString() {
		return "UserDetailsImpl [id=" + id + ", username=" + username + ", email=" + email + ", ipAddress=" + ipAddress
				+ ", password=" + password + ", authorities=" + authorities + "]";
	}

}
