package com.smartgigInternal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartgigInternal.dto.AdminDetialsDTO;
import com.smartgigInternal.service.AdminDetailsService;

@RestController
@RequestMapping("/smg/admin")
public class AdminDetailsController {

	@Autowired
	private AdminDetailsService adminDetailsService;
	
	@PostMapping(value = "/adminLogin")
	public ResponseEntity<?> adminLogin(@RequestBody AdminDetialsDTO adminDetails) {
		return new ResponseEntity<>(adminDetailsService.adminLogin(adminDetails), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAdminDetails/{id}")
	public ResponseEntity<?> getAdminDetails(@PathVariable Long id) {
		return new ResponseEntity<>(adminDetailsService.getAdminDetails(id), HttpStatus.OK);
	}
	
	@PatchMapping("/change-password/{id}")
	public ResponseEntity<?>changePassword(@PathVariable Long id ,@RequestBody AdminDetialsDTO adminLogin) {
		return new ResponseEntity<>(adminDetailsService.changePassword(id, adminLogin), HttpStatus.OK);
	}
	
	@PostMapping(value = "/access")
	public ResponseEntity<?> accessProvide(@RequestParam String name,@RequestParam String role) {
		return new ResponseEntity<>(adminDetailsService.save(name,role), HttpStatus.OK);
	}
	
	@PutMapping(value = "/removeAccess/{id}")
	public ResponseEntity<?> removeAccess(@PathVariable Long id) {
		return new ResponseEntity<>(adminDetailsService.removeAccess(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllAdmin")
	public ResponseEntity<?> getAllAdmins() {
		return new ResponseEntity<>(adminDetailsService.getallAdmins(), HttpStatus.OK);
	}
}
