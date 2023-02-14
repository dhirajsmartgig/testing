package com.smartgigInternal.serviceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.smartgigInternal.dto.EmployeeProjectRequest;
import com.smartgigInternal.dto.ProjectEmployeeDto;
import com.smartgigInternal.entity.ClientDetails;
import com.smartgigInternal.entity.EmployeeDetails;
import com.smartgigInternal.entity.ProjectDetails;
import com.smartgigInternal.entity.ProjectEmployee;
import com.smartgigInternal.repository.ClinetDetailsRepository;
import com.smartgigInternal.repository.EmployeeDetailsRepository;
import com.smartgigInternal.repository.EmployeeProjectRepository;
import com.smartgigInternal.repository.ProjectDetailsRepository;
import com.smartgigInternal.service.EmployeeProjectService;

@Service
public class EmployeeProjectServiceImpl implements EmployeeProjectService {

	@Autowired
	private EmployeeProjectRepository employeeProjectRepository;
	@Autowired
	private ProjectDetailsRepository projectDetailsRepository;
	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;
	@Autowired
	private ClinetDetailsRepository clinetDetailsRepository;

	@Override
	public Map<String, Object> saveAssignedProject(ProjectEmployeeDto projectEmployee) {
		Map<String, Object> map = new HashMap<>();
		for (String name : projectEmployee.getEmployeeNameList()) {
			ProjectEmployee projectEmp = new ProjectEmployee();
			ProjectDetails projectDetails = this.projectDetailsRepository
					.findByProjectName(projectEmployee.getProjectName());
			EmployeeDetails employeeDetails = this.employeeDetailsRepository.findByFullNameAndStatus(name, true);
			projectEmp.setEmployeeDetailsId(employeeDetails.getEmployeeDetailsId());
			projectEmp.setProjectId(projectDetails.getProjectId());
			projectEmp.setClientId(projectDetails.getClientId());
			projectEmp.setOnBoardDate(projectEmployee.getOnBoardDate());
			projectEmp.setOffBoardDate(projectEmployee.getOffBoardDate());
			projectEmp.setStatus(true);
			ProjectEmployee epmProject = this.employeeProjectRepository
					.findByEmployeeDetailsIdAndClientIdAndProjectIdAndStatus(employeeDetails.getEmployeeDetailsId(),
							projectDetails.getClientId(), projectDetails.getProjectId(), true);
			if (epmProject != null) {
				map.put("msg", "Already in this project");
				return map;
			}
			employeeProjectRepository.save(projectEmp);
		}
		map.put("msg", "assigned");

		return map;
	}

	@Override
	public List<Map<String, Object>> getListOfAssignedProject() {
		List<Map<String, Object>> responseList = new ArrayList<>();
		List<ProjectEmployee> list = employeeProjectRepository.findByStatus(true);
		for (ProjectEmployee prEmployee : list) {
			ProjectDetails projectDetails = this.projectDetailsRepository.findById(prEmployee.getProjectId()).get();
			EmployeeDetails employeeDetails = this.employeeDetailsRepository.findById(prEmployee.getEmployeeDetailsId())
					.get();
			ClientDetails clientDetails = this.clinetDetailsRepository.findById(prEmployee.getClientId()).get();
			Map<String, Object> map = new HashMap<>();
			map.put("projectName", projectDetails.getProjectName());
			map.put("EmployeeProjectId", prEmployee.getProjectEmployeeId());
			map.put("EmployeeName", employeeDetails.getFullName());
			map.put("role", employeeDetails.getRole());
			map.put("clientName", clientDetails.getClientName());
			map.put("onBoardDate", prEmployee.getOnBoardDate().toString());
			if (prEmployee.getOffBoardDate() != null) {
				map.put("offBoardDate", prEmployee.getOffBoardDate().toString());
			} else {
				map.put("offBoardDate", null);
			}
			map.put("status", true);
			responseList.add(map);
		}
		return responseList;
	}

	@Override
	public String changeStatusOfEmployeeProject(Long projectEmployeeId) {
		ProjectEmployee projectEmployee = employeeProjectRepository.findByProjectEmployeeId(projectEmployeeId);
		projectEmployee.setStatus(false);
		this.employeeProjectRepository.save(projectEmployee);
		return "deleted";
	}

	@Override
	public Map<String, Object> checkItsAllreadyAssigned(String name) {
		Map<String, Object> map = new HashMap<>();
		EmployeeDetails details = employeeDetailsRepository.findByFullNameAndStatus(name, true);
		List<ProjectEmployee> list = employeeProjectRepository
				.findByEmployeeDetailsIdAndStatus(details.getEmployeeDetailsId(), true);
		if (!list.isEmpty()) {
			map.put("msg", "He is already in project");
			map.put("noOfProject", list.size());
			map.put("DOJ", details.getDateOfJoining().toString());
			if (details.getLastWorkingDay() != null) {
				map.put("lastWorkingDate", details.getLastWorkingDay().toString());
			} else {
				map.put("lastWorkingDate", null);
			}

		} else {
			map.put("msg", "He is not in project");
			map.put("DOJ", details.getDateOfJoining().toString());
			if (details.getLastWorkingDay() != null) {
				map.put("lastWorkingDate", details.getLastWorkingDay().toString());
			} else {
				map.put("lastWorkingDate", null);
			}
		}
		return map;
	}

	public List<Map<String, Object>> getEmployeeProjectFilter(EmployeeProjectRequest carSpecs) {
		List<Map<String, Object>> responseList = new ArrayList<>();
		List<ProjectEmployee> sortedList = new ArrayList();
		ProjectEmployee empProject = new ProjectEmployee();
		List<EmployeeDetails> empList = new ArrayList();
		if (!carSpecs.getClientName().equals("")) {
			ClientDetails client = clinetDetailsRepository.findByClientNameAndStatus(carSpecs.getClientName(), true);
			if (client != null)
				empProject.setClientId(client.getClientId());
		}
		if (!carSpecs.getProjectName().equals("")) {
			ProjectDetails project = projectDetailsRepository.findByProjectName(carSpecs.getProjectName());
			if (project != null)
				empProject.setProjectId(project.getProjectId());
		}
		if (!carSpecs.getFullName().equals("")) {
			EmployeeDetails emp = employeeDetailsRepository.findByFullNameAndStatus(carSpecs.getFullName(), true);
			if (emp != null)
				empProject.setEmployeeDetailsId(emp.getEmployeeDetailsId());
		}
		if (!carSpecs.getRole().equals("") && carSpecs.getFullName().equals("")) {
			empList = employeeDetailsRepository.findByRoleAndStatus(carSpecs.getRole(), true);
		}
		empProject.setStatus(carSpecs.isStatus());
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("fromDate").withIgnorePaths("toDate");
		if (!empList.isEmpty() && !carSpecs.getRole().equals("")) {
			for (EmployeeDetails emp : empList) {
				ProjectEmployee withRole = new ProjectEmployee();
				if (empProject.getClientId() != null)
					withRole.setClientId(empProject.getClientId());
				if (empProject.getProjectId() != null)
					withRole.setProjectId(empProject.getProjectId());
				withRole.setEmployeeDetailsId(emp.getEmployeeDetailsId());
				withRole.setStatus(carSpecs.isStatus());
				List<ProjectEmployee> e = employeeProjectRepository.findAll(Example.of(withRole, matcher));
				for (ProjectEmployee ep : e)
					sortedList.add(ep);
			}
		} else {
			sortedList = employeeProjectRepository.findAll(Example.of(empProject, matcher));
		}
		for (ProjectEmployee empProj : sortedList) {
			EmployeeDetails employeeDetails = employeeDetailsRepository.findById(empProj.getEmployeeDetailsId()).get();
			ProjectDetails projectDetails = projectDetailsRepository.findById(empProj.getProjectId()).get();
			ClientDetails clientDetails = clinetDetailsRepository.findById(empProj.getClientId()).get();
			Map<String, Object> map = new HashMap<>();
			map.put("projEmpId", empProj.getProjectEmployeeId());
			map.put("onBoardDate", empProj.getOnBoardDate().toString());
			map.put("offBoardDate", empProj.getOffBoardDate().toString());
			map.put("clientName", clientDetails.getClientName());
			map.put("projectName", projectDetails.getProjectName());
			map.put("EmployeeName", employeeDetails.getFullName());
			map.put("role", employeeDetails.getRole());
			map.put("status", empProj.isStatus());
			responseList.add(map);
		}
		return responseList;
	}

}