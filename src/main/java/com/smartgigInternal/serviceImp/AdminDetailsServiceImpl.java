package com.smartgigInternal.serviceImp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartgigInternal.config.JwtUtil;
import com.smartgigInternal.config.UserDetailsServiceImpl;
import com.smartgigInternal.dto.AdminDetialsDTO;
import com.smartgigInternal.entity.DocumentDetails;
import com.smartgigInternal.entity.EmployeeDetails;
import com.smartgigInternal.repository.DocumentDetailsRepository;
import com.smartgigInternal.repository.EmployeeDetailsRepository;
import com.smartgigInternal.service.AdminDetailsService;

@Service
public class AdminDetailsServiceImpl implements AdminDetailsService {

	@Autowired 
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;
	
	@Autowired
	private DocumentDetailsRepository documentationRepo;

	@Override
	public Map<String, Object> adminLogin(AdminDetialsDTO adminDetails) {
		Map<String,Object>map=new HashMap();
		 try {
			 this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(adminDetails.getEmail(),adminDetails.getPassword()));
			 UserDetails userDetails= this.userDetailsServiceImpl.loadUserByUsername(adminDetails.getEmail());
			 EmployeeDetails admin=this.employeeDetailsRepository.findByEmailAndStatus(adminDetails.getEmail(),true);
			 DocumentDetails doc=documentationRepo.findByEmployeeDetailsId(admin.getEmployeeDetailsId());
			    map.put("msg","login Successfil");
				map.put("adminId",admin.getEmployeeDetailsId());
				map.put("isPasswordChanged",admin.isChangePassword());
				map.put("role", admin.getAccessRole());
				map.put("token", jwtUtil.generateToken(userDetails));
				map.put("adminName", admin.getFullName());
				if(doc!=null) {
				 map.put("image", doc.getEmployeeImage());
				}
				else {
					 map.put("image", null);
				}
		 }
		 catch(BadCredentialsException e) {
			 e.printStackTrace();
			// throw new Exception("Bad Credentials");
			 map.put("msg", "Bad Credetials");
			 return map;
		 }
		 catch(UsernameNotFoundException e) {
			 e.printStackTrace();
			 //throw new Exception("Bad Credetials");
			map.put("msg", "admin not found");
			return map;
		 }
		 return map;
	}

	@Override
	public Map<String,Object> getAdminDetails(Long id) {
		Map<String,Object>map=new LinkedHashMap();
		EmployeeDetails admin=this.employeeDetailsRepository.findByEmployeeDetailsId(id);
		if(admin!=null) {
			DocumentDetails doc=documentationRepo.findByEmployeeDetailsId(admin.getEmployeeDetailsId());
			map.put("adminId",admin.getEmployeeDetailsId());
		    map.put("name", admin.getFullName());
		    map.put("email", admin.getEmail());
		    map.put("designation", admin.getDegination());
		    if(doc!=null) {
		      map.put("image", doc.getEmployeeImage());
		    }
		}
		return map;
	}

	@Override
	public Map<String,Object> changePassword(Long id,AdminDetialsDTO adminDetails) {
		EmployeeDetails admin=this.employeeDetailsRepository.findByEmployeeDetailsId(id);
		Map<String,Object>map=new HashMap();
		admin.setPassword(bcrypt.encode(adminDetails.getPassword()));
		admin.setChangePassword(true);
		employeeDetailsRepository.save(admin);
		map.put("msg","password changed");
		return map;
	}

	@Override
	public String save(String name, String role) {
		EmployeeDetails admin=this.employeeDetailsRepository.findByFullNameAndStatus(name, true);
		    admin.setAccessRole(role);
		    employeeDetailsRepository.save(admin);
		return "access provided";
	}

	@Override
	public String removeAccess(Long id) {
		EmployeeDetails admin=this.employeeDetailsRepository.findByEmployeeDetailsId(id);
		admin.setAccessRole("Employee");
		employeeDetailsRepository.save(admin);
		return "access remove";
	}

	@Override
	public List<EmployeeDetails> getallAdmins() {
		return this.employeeDetailsRepository.findByAccessRoleInAndStatus(true);
	}

}
