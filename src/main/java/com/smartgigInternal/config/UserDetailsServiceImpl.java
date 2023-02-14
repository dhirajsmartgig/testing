package com.smartgigInternal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartgigInternal.entity.EmployeeDetails;
import com.smartgigInternal.repository.EmployeeDetailsRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private EmployeeDetailsRepository adminDetailsRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//feching user from database
		
		EmployeeDetails admin=adminDetailsRepository.findByEmailAndStatus(username,true);
		if(admin==null) {
			throw new UsernameNotFoundException("Could not found user!!");
		}
		CustomUserDetails customUserDetails=new CustomUserDetails(admin);
		return customUserDetails;
	}
	

}
