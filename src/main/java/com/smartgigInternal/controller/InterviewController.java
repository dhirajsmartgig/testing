package com.smartgigInternal.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.smartgigInternal.dto.InterviewDetailsDto;
import com.smartgigInternal.entity.Interviews;
import com.smartgigInternal.service.InterviewService;

@RestController
@RequestMapping("/smg/interview")
public class InterviewController {

	@Autowired
	private InterviewService interviewService;

	@PostMapping("/scheduleInterview")
	public ResponseEntity<?> saveInterviewDetails(@RequestBody Interviews interview) {
		return new ResponseEntity<>(interviewService.saveInterviewDetails(interview), HttpStatus.OK);
	}

	@GetMapping(value = "/getInterviewDetails/{candidateId}")
	public ResponseEntity<?> getinterviews(@PathVariable Long candidateId) {
		return new ResponseEntity<>(interviewService.getBycandidateId(candidateId), HttpStatus.OK);
	}

	@GetMapping(value = "/getAllInterviewDetails")
	public ResponseEntity<?> getAllInterviewDetails(@RequestParam String date, @RequestParam String role) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 Date dateAfter = sdf.parse(date);
		return new ResponseEntity<>(interviewService.getAllInterviewsDetails(dateAfter,role), HttpStatus.OK);
	}

	@PutMapping(value = "/editInterviewDetails/{id}")
	public ResponseEntity<?> editInterviwesDetails(@RequestBody InterviewDetailsDto dto,
			@PathVariable Long id) {
		return new ResponseEntity<>(interviewService.editInterviwesDetails(dto, id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getInterview/{id}")
	public ResponseEntity<?> getInterviewsDetailsById(@PathVariable Long id) {
		return new ResponseEntity<>(interviewService.getInterviewsDetailsById(id), HttpStatus.OK);
	}

}