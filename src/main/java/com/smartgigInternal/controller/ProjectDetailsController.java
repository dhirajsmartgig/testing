package com.smartgigInternal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartgigInternal.dto.ProjectDetailsDTO;
import com.smartgigInternal.service.ProjectDetailsService;

@RestController
@RequestMapping("/smg/project")
public class ProjectDetailsController {

	@Autowired
	private ProjectDetailsService projectDetailsService;

	@PostMapping(value = "/saveProjectDetails")
	public ResponseEntity<?> saveProjectDetails(@RequestBody ProjectDetailsDTO projectDetails) {
		return new ResponseEntity<>(projectDetailsService.saveProjectDetails(projectDetails), HttpStatus.OK);
	}

	@PutMapping(value = "/editProjectDetails/{id}")
	public ResponseEntity<?> editProjectDetails(@RequestBody ProjectDetailsDTO dto, @PathVariable Long id) {
		return new ResponseEntity<>(projectDetailsService.editProjectDetails(dto, id), HttpStatus.OK);
	}

	@GetMapping(value = "/getAllProjectDetails")
	public ResponseEntity<?> getAllProjectDetails() {
		return new ResponseEntity<>(projectDetailsService.getAllProjectDetails(), HttpStatus.OK);
	}

	@GetMapping(value = "/getProjectById/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable Long projectId) {
		return new ResponseEntity<>(projectDetailsService.getProjectById(projectId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getNoOffActiveEmp/{projectId}")
	public ResponseEntity<?> getNoOffActiveEmp(@PathVariable Long projectId) {
		return new ResponseEntity<>(projectDetailsService.getNoOffActiveEmp(projectId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/ProjectFilter")
	public ResponseEntity<?> projectFilter(@RequestParam String clientName,@RequestParam String status ) {
		return new ResponseEntity<>(projectDetailsService.projectFilter(clientName,status), HttpStatus.OK);
	}
}
