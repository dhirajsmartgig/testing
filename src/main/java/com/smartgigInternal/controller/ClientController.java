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
import org.springframework.web.bind.annotation.RestController;

import com.smartgigInternal.dto.ClientDetailsDto;
import com.smartgigInternal.entity.ClientDetails;
import com.smartgigInternal.service.ClientDetailsService;

@RestController
@RequestMapping("/smg/client")
public class ClientController {
	

	@Autowired
	private ClientDetailsService clientDetailsService;

	@PostMapping("/addClient")
	public ResponseEntity<?> addClient(@RequestBody ClientDetails clientDetails) {
		return new ResponseEntity<>(clientDetailsService.saveClient(clientDetails), HttpStatus.OK);
	}

	@GetMapping(value = "/getClientDetails/{clientId}")
	public ResponseEntity<?> getClientDetails(@PathVariable long clientId) {
		return new ResponseEntity<>(clientDetailsService.getClintDetails(clientId), HttpStatus.OK);
	}

	@PostMapping(value = "/deleteClientDetails/{clientId}")
	public ResponseEntity<?> deleteClientDetails(@PathVariable long clientId) {
		return new ResponseEntity<>(clientDetailsService.changeStatusOfClient(clientId), HttpStatus.OK);
	}

	@GetMapping(value = "/getAllClientDetails")
	public ResponseEntity<?> getAllClientDetail() {
		return new ResponseEntity<>(clientDetailsService.getAllClientDetails(), HttpStatus.OK);
	}

	@PutMapping(value = "/editClientDetails/{clientId}")
	public ResponseEntity<?> editClientDetails(@RequestBody ClientDetailsDto dto, @PathVariable Long clientId) {
		return new ResponseEntity<>(clientDetailsService.editClientDetails(dto, clientId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getNoOfActiveProjectAndEmp/{clientId}")
	public ResponseEntity<?> getNoOfActiveProjectAndEmp( @PathVariable Long clientId) {
		return new ResponseEntity<>(clientDetailsService.getNoOfActiveProjectAndEmp(clientId), HttpStatus.OK);
	}

}