package com.smartgigInternal.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smartgigInternal.entity.EmployeeDetails;

public class CustomUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private EmployeeDetails adminDetials;

	public CustomUserDetails(EmployeeDetails adminDetials) {
		super();
		this.adminDetials = adminDetials;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		// SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority();
		return null;// List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {

		return adminDetials.getPassword();
	}

	@Override
	public String getUsername() {

		return adminDetials.getEmail();
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

}
