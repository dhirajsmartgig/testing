package com.smartgigInternal.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartgigInternal.entity.Laptop;
import com.smartgigInternal.service.LaptopService;

@RestController
@RequestMapping("/smg/laptop")
public class LaptopController {

	@Autowired
	private LaptopService laptopService;

	@PostMapping("/save")
	public ResponseEntity<?> saveLaptop(@RequestBody Laptop laptop) {
		return new ResponseEntity<>(laptopService.saveLaptop(laptop), HttpStatus.OK);
	}

	@GetMapping("/getAllLaptop/{status}")
	public ResponseEntity<?> getAllLaptop(@PathVariable boolean status) {
		return new ResponseEntity<>(laptopService.getAllLaptop(status), HttpStatus.OK);
	}

	@GetMapping("/getLaptop/{id}")
	public ResponseEntity<?> getLaptop(@PathVariable("id") Long id) {
		return new ResponseEntity<>(laptopService.getLaptop(id), HttpStatus.OK);
	}

	@PutMapping("/updateLaptopDetails")
	public ResponseEntity<?> updateLaptopDetails(@RequestBody Laptop laptop) {
		return new ResponseEntity<>(laptopService.updateLaptopDetails(laptop), HttpStatus.OK);
	}
	
	@GetMapping("/laptopStock")
	public ResponseEntity<?> laptopStock() {
		return new ResponseEntity<>(laptopService.laptopStock(), HttpStatus.OK);
	}
	
	@PutMapping("/changeStatus/{id}")
	public ResponseEntity<?> changeStatus(@PathVariable Long id) {
		return new ResponseEntity<>(laptopService.changeStatus(id), HttpStatus.OK);
	}
	
	@PostMapping("/laptopBulkUpload")
	public ResponseEntity<?> excelReader(@RequestPart(value = "excel", required = true) MultipartFile bulkUploadExcel) throws IOException {
		List<Map<String, String>> saveBulkLaptop = laptopService.saveBulkLaptopDetails(bulkUploadExcel);
		return new ResponseEntity<>(saveBulkLaptop, HttpStatus.OK);
	}
	
	@GetMapping("/getAssignedLaptop")
	public ResponseEntity<?> getAssignedLaptop() {
		return new ResponseEntity<>(laptopService.getAssignedLaptop(), HttpStatus.OK);
	}
}
