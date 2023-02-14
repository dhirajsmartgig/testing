package com.smartgigInternal.service;

import java.util.List;
import java.util.Map;

import com.smartgigInternal.dto.AdminDetialsDTO;
import com.smartgigInternal.entity.EmployeeDetails;

public interface AdminDetailsService {

	Map<String,Object>adminLogin(AdminDetialsDTO adminDetails);
	Map<String,Object> getAdminDetails(Long id);
	Map<String,Object> changePassword(Long id,AdminDetialsDTO adminDetails);
	String save(String name,String role);
	String removeAccess(Long id);
	List<EmployeeDetails>getallAdmins();
}
