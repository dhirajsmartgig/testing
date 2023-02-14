package com.smartgigInternal.controller;

import java.io.IOException;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartgigInternal.dto.EmployeeDTO;
import com.smartgigInternal.dto.EmployeeLeaveDto;
import com.smartgigInternal.dto.Hike;
import com.smartgigInternal.entity.DocumentDetails;
import com.smartgigInternal.entity.EmployeeDetails;
import com.smartgigInternal.service.EmployeeDetailsService;
import com.smartgigInternal.serviceImp.S3bucketStorageService;

@RestController
@RequestMapping("/smg/employee/")
public class EmployeeController {

	@Autowired
	private EmployeeDetailsService employeeDetailsService;

	@Autowired
	private S3bucketStorageService s3Bucket;

	@PostMapping(value = "/saveEmployeeDetails")
	public ResponseEntity<?> saveEmployeeDetails(@RequestBody EmployeeDetails employeeDetails) {
		return new ResponseEntity<>(employeeDetailsService.saveEmployeeDetails(employeeDetails), HttpStatus.OK);
	}

	@GetMapping(value = "/getEmployeeDetails/{id}")
	public ResponseEntity<?> getEmployeeDetails(@PathVariable Long id) {
		return new ResponseEntity<>(employeeDetailsService.getEmployeeDetails(id), HttpStatus.OK);
	}

	@PostMapping(value = "/deleteEmployee/{id}")
	public ResponseEntity<?> deleteEmployeeDetails(@PathVariable Long id) {
		return new ResponseEntity<>(employeeDetailsService.changeStatusOfEmployee(id), HttpStatus.OK);
	}

	@PutMapping(value = "/editEmployeeDetails/{id}")
	public ResponseEntity<?> editEmployeeDetails(@RequestBody EmployeeDTO dto, @PathVariable Long id) {
		return new ResponseEntity<>(employeeDetailsService.editEmployeeDetails(dto, id), HttpStatus.OK);
	}

	@GetMapping(value = "/getAllEmployeeList")
	public ResponseEntity<?> getAllEmployeeByStatus() {
		return new ResponseEntity<>(employeeDetailsService.getAllEmployeeByStatus(), HttpStatus.OK);
	}

	@PostMapping("/uploadoc/{employeeDetailsId}")
	public ResponseEntity<?> saveDoc(@PathVariable Long employeeDetailsId,
			@RequestPart(value = "certificate10th", required = false) MultipartFile certificate10th,
			@RequestPart(value = "certificate12th", required = false) MultipartFile certificate12th,
			@RequestPart(value = "graduation", required = false) MultipartFile graduation,
			@RequestPart(value = "postGraduation", required = false) MultipartFile postGraduation,
			@RequestPart(value = "paySlip", required = false) MultipartFile paySlip,
			@RequestPart(value = "offerLetter1", required = false) MultipartFile offerLetter1,
			@RequestPart(value = "offerLetter2", required = false) MultipartFile offerLetter2,
			@RequestPart(value = "offerLetter3", required = false) MultipartFile offerLetter3,
			@RequestPart(value = "employeeImage", required = false) MultipartFile employeeImage,
			@RequestPart(value = "resume", required = false) MultipartFile resume

	) throws IOException {

		DocumentDetails documentation = employeeDetailsService.findByEmployeeDetailsId(employeeDetailsId);

		if (documentation != null) {
			documentation.setDocumentationId(documentation.getDocumentationId());
			if (!certificate10th.isEmpty()) {
				documentation.setCertificate10th(s3Bucket.uploadFile(certificate10th));
			}
			if (!certificate12th.isEmpty()) {
				documentation.setCertificate12th(s3Bucket.uploadFile(certificate12th));
			}
			if (!graduation.isEmpty()) {
				documentation.setGraduation(s3Bucket.uploadFile(graduation));
			}
			if (!postGraduation.isEmpty()) {
				documentation.setPostGraduation(s3Bucket.uploadFile(postGraduation));
			}
			if (!paySlip.isEmpty()) {
				documentation.setPaySlip(s3Bucket.uploadFile(paySlip));
			}
			if (!employeeImage.isEmpty()) {
				documentation.setEmployeeImage(s3Bucket.uploadFile(employeeImage));
			}
			if (!offerLetter1.isEmpty()) {
				documentation.setOfferLetter1(s3Bucket.uploadFile(offerLetter1));
			}
			if (!offerLetter2.isEmpty()) {
				documentation.setOfferLetter2(s3Bucket.uploadFile(offerLetter2));
			}
			if (!offerLetter3.isEmpty()) {
				documentation.setOfferLetter3(s3Bucket.uploadFile(offerLetter3));
			}
			if (!resume.isEmpty()) {
				documentation.setResume(s3Bucket.uploadFile(resume));
			}
		} else {
			documentation = new DocumentDetails();
			documentation.setEmployeeDetailsId(employeeDetailsId);
			if (!certificate10th.isEmpty()) {
				documentation.setCertificate10th(s3Bucket.uploadFile(certificate10th));
			}
			if (!certificate12th.isEmpty()) {
				documentation.setCertificate12th(s3Bucket.uploadFile(certificate12th));
			}
			if (!graduation.isEmpty()) {
				documentation.setGraduation(s3Bucket.uploadFile(graduation));
			}
			if (!postGraduation.isEmpty()) {
				documentation.setPostGraduation(s3Bucket.uploadFile(postGraduation));
			}
			if (!paySlip.isEmpty()) {
				documentation.setPaySlip(s3Bucket.uploadFile(paySlip));
			}
			if (!employeeImage.isEmpty()) {
				documentation.setEmployeeImage(s3Bucket.uploadFile(employeeImage));
			}
			if (!offerLetter1.isEmpty()) {
				documentation.setOfferLetter1(s3Bucket.uploadFile(offerLetter1));
			}
			if (!offerLetter2.isEmpty()) {
				documentation.setOfferLetter2(s3Bucket.uploadFile(offerLetter2));
			}
			if (!offerLetter3.isEmpty()) {
				documentation.setOfferLetter3(s3Bucket.uploadFile(offerLetter3));
			}
			if (!resume.isEmpty()) {
				documentation.setResume(s3Bucket.uploadFile(resume));
			}
		}

		return new ResponseEntity<>(employeeDetailsService.saveEmployeeDocuments(documentation), HttpStatus.OK);
	}

	@GetMapping(value = "/getTechEmp")
	public ResponseEntity<?> getTechnicalEmp() {
		return new ResponseEntity<>(employeeDetailsService.getTechnicalEmp(), HttpStatus.OK);
	}

	@PostMapping("/image/{employeeDetailsId}")
	public ResponseEntity<?> saveFile(@PathVariable Long employeeDetailsId,
			@RequestPart(value = "image", required = false) MultipartFile certificate10th) throws IOException {
		// DocumentDetails documentation =new DocumentDetails();
		DocumentDetails documentation = employeeDetailsService.findByEmployeeDetailsId(employeeDetailsId);
		documentation.setOfferLetter3(s3Bucket.uploadFile(certificate10th));
		return new ResponseEntity<>(employeeDetailsService.saveEmployeeDocuments(documentation), HttpStatus.OK);
	}

	@GetMapping("/getImage/{employeeDetailsId}")
	public ResponseEntity<?> getDocs(@PathVariable Long employeeDetailsId) {
		return new ResponseEntity<>(employeeDetailsService.getDocs(employeeDetailsId), HttpStatus.OK);
	}

	@GetMapping(value = "/getemplyeeFilter")
	public ResponseEntity<?> getEmployeeDetails1(@RequestParam String fullname, @RequestParam String role,
			@RequestParam String currentlocation, @RequestParam boolean status) {
		return new ResponseEntity<>(employeeDetailsService.getByrollandfullandcurrentlocationAndstatus(fullname, role,
				currentlocation, status), HttpStatus.OK);
	}

	@GetMapping("/getCarBySpecs")
	public ResponseEntity<?> getCarsBySpecs(@RequestBody EmployeeDetails carSpecs) {
		// return ResponseEntity.ok(employeeDetailsService.getCarsBySpecs(carSpecs));
		return new ResponseEntity<>(employeeDetailsService.getCarsBySpecs(carSpecs), HttpStatus.OK);
	}

	@PostMapping("/empBulkUpload")
	public ResponseEntity<?> excelReader(@RequestPart(value = "excel", required = true) MultipartFile bulkUploadExcel) {
		List<Map<String, String>> saveBulkEmployeeDetails = employeeDetailsService
				.saveBulkEmployeeDetails(bulkUploadExcel);
		return new ResponseEntity<>(saveBulkEmployeeDetails, HttpStatus.OK);
	}

	@GetMapping("/getAllskill")
	public ResponseEntity<?> getAllSkill() {
		return new ResponseEntity<>(employeeDetailsService.getAllSkill(), HttpStatus.OK);
	}


	@PostMapping("/hike")
	public ResponseEntity<?> empHike(@RequestBody Hike hike) {
		return new ResponseEntity<>(employeeDetailsService.empHike(hike), HttpStatus.OK);
	}

	@GetMapping("/getHikeInfoByEmpId/{id}")
	public ResponseEntity<?> getHikeInfoByEmpId(@PathVariable("id") long empId) {
		return new ResponseEntity<>(employeeDetailsService.getHikeInfoByEmpId(empId), HttpStatus.OK);
	}

	@PutMapping("/updateEmpPhoto/{id}")
	public ResponseEntity<?> updateEmpPhoto(
			@RequestPart(value = "employeeImage", required = true) MultipartFile employeeImage,
			@PathVariable("id") long empDetailsId) {
		DocumentDetails documentation = employeeDetailsService.findByEmployeeDetailsId(empDetailsId);
		documentation.setEmployeeImage(s3Bucket.uploadFile(employeeImage));
		return new ResponseEntity<>(employeeDetailsService.saveEmployeeDocuments(documentation), HttpStatus.OK);
	}

	
	@GetMapping("/hikeEligibility")
	public ResponseEntity<?> hikeEligibility() throws ParseException {
		return new ResponseEntity<>(employeeDetailsService.hikeEligibility(), HttpStatus.OK);
	}

	@GetMapping("/getAllEmployeeLeaves")
	public ResponseEntity<?> getAllEmployeeLeaves() {
		return new ResponseEntity<>(employeeDetailsService.getAllEmployeeLeaves(), HttpStatus.OK);
	}

	@GetMapping("/getEmployeeLeaveById/{id}")
	public ResponseEntity<?> getEmployeeLeaveById(@PathVariable Long id) {
		return new ResponseEntity<>(employeeDetailsService.getEmployeeLeaveById(id), HttpStatus.OK);
	}
	
	@PostMapping("/empLeaveApply")
	public ResponseEntity<?> empLeaveApply(@RequestBody EmployeeLeaveDto employeeLeaveDto) {
		return new ResponseEntity<>(employeeDetailsService.empLeaveApply(employeeLeaveDto), HttpStatus.OK);
	}
	
	@GetMapping("/getEmployeeLeaveHistoryById/{id}")
	public ResponseEntity<?> getEmployeeLeaveHistoryById(@PathVariable Long id) {
		return new ResponseEntity<>(employeeDetailsService.getEmployeeLeaveHistoryById(id), HttpStatus.OK);
	}
	
	
	
	

}
