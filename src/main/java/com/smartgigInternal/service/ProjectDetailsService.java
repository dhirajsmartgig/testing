package com.smartgigInternal.service;

import java.util.List;
import java.util.Map;

import com.smartgigInternal.dto.ProjectDetailsDTO;
import com.smartgigInternal.entity.ClientDetails;

public interface ProjectDetailsService {

	Map<String, Object> saveProjectDetails(ProjectDetailsDTO projectDetails);

	Map<String, Object> editProjectDetails(ProjectDetailsDTO dto, Long id);

	List<Map<String, Object>> getAllProjectDetails();
	
	Map<String,Object> getProjectById(Long projectId);

	Map<String,Object> getNoOffActiveEmp(Long projectId);

	List<Map<String, Object>> projectFilter(String clientName, String status);

}
