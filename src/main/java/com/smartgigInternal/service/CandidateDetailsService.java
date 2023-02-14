package com.smartgigInternal.service;

import java.util.List;
import java.util.Map;

import com.smartgigInternal.dto.CandidateDetailsDTO;
import com.smartgigInternal.entity.CandidateDetails;
import com.smartgigInternal.entity.DocumentDetails;

public interface CandidateDetailsService {

	public Map<String, Object> saveCandidateDetails(CandidateDetails candidateDetails);

	public CandidateDetails getCandidateById(long candidId);

	public List<CandidateDetails> getAllCandidate(String status,String role);

    public String editCandidateDetails(long candidId, CandidateDetailsDTO candidate);

	public String changeCandidateStatus(long candidId, String status);

	public List<String> getAllRole();
	
	//public DocumentDetails editEmployeeDetailsId(long id);

	//public Object getAllCandidate(String status, String role);
	
	//public List<Map<String, Object>> getAllInterviewsDetails(Date date, String role);
}

