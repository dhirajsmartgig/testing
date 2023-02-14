package com.smartgigInternal.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smartgigInternal.dto.CandidateDetailsDTO;
import com.smartgigInternal.entity.CandidateDetails;
import com.smartgigInternal.service.CandidateDetailsService;
import com.smartgigInternal.serviceImp.S3bucketStorageService;

@RestController
@RequestMapping("/smg/candidate")
public class CandidateDetailsController {

	@Autowired
	CandidateDetailsService candidateDetailsService;

	@Autowired
	S3bucketStorageService s3Bucket;

	@PostMapping("/saveCandidateDetails")
	public ResponseEntity<?> savecandidateDetails(@RequestPart CandidateDetails candidateDetails,
			@RequestPart(value = "resume", required = false) MultipartFile resume) throws IOException {
		candidateDetails.setResume(s3Bucket.uploadCandidateResume(resume));
		return new ResponseEntity<>(candidateDetailsService.saveCandidateDetails(candidateDetails), HttpStatus.OK);
	}

	@GetMapping("/getCandidateById/{candidId}")
	public ResponseEntity<?> getCandidateById(@PathVariable long candidId) {
		return new ResponseEntity<>(candidateDetailsService.getCandidateById(candidId), HttpStatus.OK);
	}

	@GetMapping("/getAllCandidate")
	public ResponseEntity<?> getAllCandidate(@RequestParam String status, @RequestParam String role) {
		return new ResponseEntity<>(candidateDetailsService.getAllCandidate(status,role), HttpStatus.OK);
	}
	
	@PutMapping("/editCandidateDetails/{candidId}")
	public ResponseEntity<?> editCandidateDetails(@PathVariable long candidId,
			@RequestPart(value = "candidateDetails", required = false) CandidateDetailsDTO candidateDetails,
			@RequestPart(value = "resume", required = false) MultipartFile resume) throws IOException {
		if(!resume.isEmpty()) {
		    candidateDetails.setResume(s3Bucket.uploadCandidateResume(resume));
		}
		return new ResponseEntity<>(candidateDetailsService.editCandidateDetails(candidId,candidateDetails), HttpStatus.OK);
	}
	
	@PutMapping("/changeStatus/{candidId}")
	public ResponseEntity<?> changeCandidateStatus(@PathVariable long candidId,@RequestParam String status) {
		return new ResponseEntity<>(candidateDetailsService.changeCandidateStatus(candidId,status), HttpStatus.OK);
	}
	
	@GetMapping("/getAllrole")
	public ResponseEntity<?>getAllRole(){
		return new ResponseEntity<>(candidateDetailsService.getAllRole(),HttpStatus.OK);
	}
}
