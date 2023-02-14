package com.smartgigInternal.serviceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartgigInternal.dto.ProjectDetailsDTO;
import com.smartgigInternal.entity.ClientDetails;
import com.smartgigInternal.entity.ProjectDetails;
import com.smartgigInternal.entity.ProjectEmployee;
import com.smartgigInternal.repository.ClinetDetailsRepository;
import com.smartgigInternal.repository.EmployeeProjectRepository;
import com.smartgigInternal.repository.ProjectDetailsRepository;
import com.smartgigInternal.service.ProjectDetailsService;

@Service
public class ProjectDetailsServiceImpl implements ProjectDetailsService {

	@Autowired
	private ProjectDetailsRepository projectDetailsRepository;
	@Autowired
	private ClinetDetailsRepository clinetDetailsRepository;
	@Autowired
	private EmployeeProjectRepository employeeProjectRepository;

	@Override
	public Map<String, Object> saveProjectDetails(ProjectDetailsDTO projectDetails) {
		Map<String, Object> map = new HashMap<>();
		ProjectDetails project = new ProjectDetails();
		ClientDetails clientDetails = clinetDetailsRepository.findByClientNameAndStatus(projectDetails.getClientName(),
				true);
		ProjectDetails projectDe = this.projectDetailsRepository
				.findByProjectNameAndClientId(projectDetails.getProjectName(), clientDetails.getClientId());
		if (projectDe != null) {
			map.put("message", "already present");
		} else {
			project.setClientId(clientDetails.getClientId());
			project.setProjectName(projectDetails.getProjectName());
			project.setProjectDescription(projectDetails.getProjectDescription());
			project.setReportingManager(projectDetails.getReportingManager());
			project.setClientEmail(projectDetails.getClientEmail());
			project.setStatus(projectDetails.getStatus());
			project.setStartDate(projectDetails.getStartDate());
			project.setEndDate(projectDetails.getEndDate());
			projectDetailsRepository.save(project);
			map.put("message", "details was saved into the database.......");
		}
		return map;	
	}

	@Override
	public Map<String, Object> editProjectDetails(ProjectDetailsDTO dto, Long id) {
		ProjectDetails exsProjectDetails = projectDetailsRepository.findByProjectId(id);
		ClientDetails clientDetails = clinetDetailsRepository.findByClientNameAndStatus(dto.getClientName(), true);
		Map<String, Object> map = new HashMap<>();
//		if(dto.getProject()) {
//	     	ProjectDetails projectDe=this.projectDetailsRepository.findByProjectNameAndClientId(dto.getProjectName(),clientDetails.getClientId());
//		   if(projectDe!=null) {
//			map.put("message", "already present");
//			return map;
//		}
		// }
		if (exsProjectDetails != null) {
			if (dto.getClientName() != null)
				exsProjectDetails.setClientId(clientDetails.getClientId());
			if (dto.getProjectDescription() != null)
				exsProjectDetails.setProjectDescription(dto.getProjectDescription());
			if (dto.getReportingManager() != null)
				exsProjectDetails.setReportingManager(dto.getReportingManager());
			if (dto.getClientEmail() != null)
				exsProjectDetails.setClientEmail(dto.getClientEmail());
			if (dto.getProjectName() != null)
				exsProjectDetails.setProjectName(dto.getProjectName());
			if (dto.getStatus() != null)
				exsProjectDetails.setStatus(dto.getStatus());
			if (dto.getStartDate() != null)
				exsProjectDetails.setStartDate(dto.getStartDate());
			if(dto.getEndDate() != null)
				exsProjectDetails.setEndDate(dto.getEndDate());
			ProjectDetails project = projectDetailsRepository.save(exsProjectDetails);
			if (project.getStatus().equals("Completed")) {
				List<ProjectEmployee> assignedPro = employeeProjectRepository
						.findByProjectIdAndStatus(project.getProjectId(), true);
				for (ProjectEmployee pro : assignedPro) {
					pro.setStatus(false);
					employeeProjectRepository.save(pro);
				}
			}
			String message = null;
			message = "details is updated successfully";
			map.put("message", message);
		}

		return map;
	}

	@Override
	public List<Map<String, Object>> getAllProjectDetails() {
		List<Map<String, Object>> responseList = new ArrayList();
		List<ProjectDetails> list = projectDetailsRepository.findAll();
		for (ProjectDetails project : list) {
			ClientDetails clientDetails = clinetDetailsRepository.findByClientId(project.getClientId());
			Map<String, Object> map = new HashMap();
			map.put("projectDes", project.getProjectDescription());
			map.put("projectId", project.getProjectId());
			map.put("projectName", project.getProjectName());
			map.put("projectStatus", project.getStatus());
			map.put("ClientName", clientDetails.getClientName());
			map.put("reportingManager", project.getReportingManager());
			map.put("clientEmail", project.getClientEmail());
			//map.put("startDate",project.getStartDate());
			if(project.getStartDate() != null)
				map.put("startDate", project.getStartDate() .toString());
			else {
				map.put("startDate",null);
			}
			if(project.getEndDate() != null)
				map.put("endDate", project.getEndDate().toString());
			else {
				map.put("endDate",null);
			}
			
			//map.put("endDate", project.getEndDate());
			responseList.add(map);
		}
		return responseList;
	}

	@Override
	public Map<String, Object> getProjectById(Long projectId) {
		ProjectDetails project = projectDetailsRepository.findByProjectId(projectId);
		ClientDetails clientDetails = clinetDetailsRepository.findByClientId(project.getClientId());
		Map<String, Object> map = new HashMap();
		map.put("projectDes", project.getProjectDescription());
		map.put("projectId", project.getProjectId());
		map.put("projectName", project.getProjectName());
		map.put("projectStatus", project.getStatus());
		map.put("ClientName", clientDetails.getClientName());
		map.put("reportingManager", project.getReportingManager());
		map.put("clientEmail", project.getClientEmail());
		if(project.getStartDate() != null)
			map.put("startDate", project.getStartDate() .toString());
		else {
			map.put("startDate",null);
		}
		if(project.getEndDate() != null)
			map.put("endDate", project.getEndDate().toString());
		else {
			map.put("endDate",null);
		}
		return map;
	}

	@Override
	public Map<String, Object> getNoOffActiveEmp(Long projectId) {
		List<ProjectEmployee> assignedPro = employeeProjectRepository.findByProjectIdAndStatus(projectId, true);
		Map<String, Object> map = new HashMap();
		map.put("NoOfActiveEmp", assignedPro.size());
		return map;
	}

	@Override
	public List<Map<String, Object>> projectFilter(String clientName, String status) {
		List<Map<String, Object>> responseList = new ArrayList();
		List<ProjectDetails> list = new ArrayList();
		ClientDetails client = clinetDetailsRepository.findByClientNameAndStatus(clientName, true);
		if (clientName != "" && status == "") {
			list = this.projectDetailsRepository.findByClientId(client.getClientId());
		} else if (clientName == "" && status != "") {
			list = this.projectDetailsRepository.findByStatus(status);
		} else if (clientName != "" && status != "") {
			list = this.projectDetailsRepository.findByClientIdAndStatus(client.getClientId(), status);
		}
		for (ProjectDetails project : list) {
			ClientDetails clientDetails = clinetDetailsRepository.findByClientId(project.getClientId());
			Map<String, Object> map = new HashMap();
			map.put("projectDes", project.getProjectDescription());
			map.put("projectId", project.getProjectId());
			map.put("projectName", project.getProjectName());
			map.put("projectStatus", project.getStatus());
			map.put("ClientName", clientDetails.getClientName());
			map.put("reportingManager", project.getReportingManager());
			responseList.add(map);
		}
		return responseList;
	}
}
