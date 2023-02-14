package com.smartgigInternal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgigInternal.dto.EmployeeProjectRequest;
import com.smartgigInternal.dto.ProjectEmployeeDto;
import com.smartgigInternal.service.EmployeeProjectService;

@RestController
@RequestMapping("/smg/assignedProject")
public class EmployeeProjectController {
	@Autowired
	private EmployeeProjectService employeeProjectService;

	@PostMapping(value = "/saveAssignedProject")
	public ResponseEntity<?> saveEmployeeProject(@RequestBody ProjectEmployeeDto projectEmployee) {
		return new ResponseEntity<>(employeeProjectService.saveAssignedProject(projectEmployee), HttpStatus.OK);
	}

	@GetMapping(value = "/getListOfAssignedProject")
	public ResponseEntity<?> getListOfAssignedProject() {
		return new ResponseEntity<>(employeeProjectService.getListOfAssignedProject(), HttpStatus.OK);
	}

	@PostMapping(value = "/deleteEmployeeProject/{projectEmployeeId}")
	public ResponseEntity<?> deleteEmployeeProject(@PathVariable Long projectEmployeeId) {
		return new ResponseEntity<>(employeeProjectService.changeStatusOfEmployeeProject(projectEmployeeId),
				HttpStatus.OK);
	}

	@PostMapping(value = "/checkIsAllReadyAssigned/{name}")
	public ResponseEntity<?> checkItsAllreadyAssigned(@PathVariable String name) {
		return new ResponseEntity<>(employeeProjectService.checkItsAllreadyAssigned(name), HttpStatus.OK);
	}

//	@PostMapping(value="/filter")
//	public ResponseEntity<?>assignedProjectFilter(AssignedProjectFilterDto dto){
//		return new ResponseEntity<>(employeeProjectService.assignedProjectFilter(dto),HttpStatus.OK);
//	}

	@PostMapping(value = "/getEmployeeProjectFilter")
	public ResponseEntity<?> getEmployeeBillingFilter(@RequestBody EmployeeProjectRequest carSpecs) {
		return new ResponseEntity<>(employeeProjectService.getEmployeeProjectFilter(carSpecs), HttpStatus.OK);
	}
}