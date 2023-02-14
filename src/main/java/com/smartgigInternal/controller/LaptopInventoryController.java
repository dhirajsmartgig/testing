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

import com.smartgigInternal.entity.LaptopInventory;
import com.smartgigInternal.service.LaptopInventoryService;

@RestController
@RequestMapping("/smg/assignedLap")
public class LaptopInventoryController {

	@Autowired
	public LaptopInventoryService laptopInventoryService;

	@PostMapping("/assignedLap")
	public ResponseEntity<?> saveLaptop(@RequestBody LaptopInventory laptopInventory) {
		return new ResponseEntity<>(laptopInventoryService.saveLaptop(laptopInventory), HttpStatus.OK);
	}

	@GetMapping("/getAllAssignedLaptops/{isReturn}")
	public ResponseEntity<?> getAllAssignedLaptops(@PathVariable boolean isReturn) {
		return new ResponseEntity<>(laptopInventoryService.getAllAssignedLaptops(isReturn), HttpStatus.OK);
	}

	@GetMapping("/getAssignedLaptops/{id}")
	public ResponseEntity<?> getAssignedLaptops(@PathVariable("id") Long id) {
		return new ResponseEntity<>(laptopInventoryService.getAssignedLaptops(id), HttpStatus.OK);
	}

	@PutMapping("/editAssignedLaptops")
	public ResponseEntity<?> editAssignedLaptops(@RequestBody LaptopInventory laptopInventory) {
		return new ResponseEntity<>(laptopInventoryService.editAssignedLaptops(laptopInventory), HttpStatus.OK);
	}

	@GetMapping("/getAssignedLaptopsDetails/{employeeDetailsId}")
	public ResponseEntity<?> getAssignedLaptopsDetails(@PathVariable("employeeDetailsId") Long employeeDetailsId) {
		return new ResponseEntity<>(laptopInventoryService.getAssignedLaptopsDetails(employeeDetailsId), HttpStatus.OK);
	}
    
	@GetMapping("/getAssignedLaptopsDetailsbyid/{employeeDetailsId}")
	public ResponseEntity<?> getAssignedLaptopsDetailsbyid(@PathVariable("employeeDetailsId") Long employeeDetailsId) {
		return new ResponseEntity<>(laptopInventoryService.getAssignedLaptopsDetailsbyid(employeeDetailsId), HttpStatus.OK);
	}

}
