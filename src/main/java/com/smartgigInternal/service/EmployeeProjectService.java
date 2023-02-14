package com.smartgigInternal.service;

import java.util.List;
import java.util.Map;

import com.smartgigInternal.dto.EmployeeProjectRequest;
import com.smartgigInternal.dto.ProjectEmployeeDto;

public interface EmployeeProjectService {

	Map<String, Object> saveAssignedProject(ProjectEmployeeDto projectEmployee);

	List<Map<String, Object>> getListOfAssignedProject();

	public String changeStatusOfEmployeeProject(Long projectEmployeeId);

	Map<String, Object> checkItsAllreadyAssigned(String name);

	List<Map<String, Object>> getEmployeeProjectFilter(EmployeeProjectRequest employeeProjectRequest);
}