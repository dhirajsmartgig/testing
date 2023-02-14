package com.smartgigInternal.serviceImp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartgigInternal.dto.CandidateDetailsDTO;
import com.smartgigInternal.entity.CandidateDetails;
import com.smartgigInternal.repository.CandidateDetailsRepository;
import com.smartgigInternal.service.CandidateDetailsService;

  @Service
public class CandidateDetailsServiceImpl implements CandidateDetailsService {

	@Autowired
	private CandidateDetailsRepository candidateDetailsRepository;

	@Override
	public Map<String, Object> saveCandidateDetails(CandidateDetails candidateDetails) {
		CandidateDetails candidate=candidateDetailsRepository.save(candidateDetails);
		 Map<String, Object> map = new HashMap<>();
        if(candidate!=null) {
		    map.put("msg", "candidate added");
		    map.put("candidateId", candidate.getId());
        }
		return map;
	}

	@Override
	public CandidateDetails getCandidateById(long candidId) {
		return candidateDetailsRepository.findById(candidId).get();
	}

	@Override
	public List<CandidateDetails> getAllCandidate(String status, String role) {
		if (!status.equals("") && role.equals("")) {
			return candidateDetailsRepository.findByStatus(status);
		} else if (status.equals("") && !role.equals("")) {
			return candidateDetailsRepository.findByAppliedFor(role);
		} else if (!status.equals("") && !role.equals("")) {
			return candidateDetailsRepository.findByAppliedForAndStatus(role, status);
		}	
		return candidateDetailsRepository.findAll();
	}
	
	
	
	@Override
	public String editCandidateDetails(long candidId,CandidateDetailsDTO candidate) {
    CandidateDetails existingcandidate=candidateDetailsRepository.getById(candidId);
		if(candidate.getCandidateName()!=null)
			existingcandidate.setCandidateName(candidate.getCandidateName());
		if(candidate.getAlterNo()!=null)
			existingcandidate.setAlterNo(candidate.getAlterNo());
		if(candidate.getAppliedFor()!=null)
			existingcandidate.setAppliedFor(candidate.getAppliedFor());
		if(candidate.getCurrentOrg()!=null)
			existingcandidate.setCurrentOrg(candidate.getCurrentOrg());
		if(candidate.getCurrentCtc()!=null)
			existingcandidate.setCurrentCtc(candidate.getCurrentCtc());
		if(candidate.getDob()!=null)
			existingcandidate.setDob(candidate.getDob());
		if(candidate.getEmail()!=null)
			existingcandidate.setEmail(candidate.getEmail());
		if(candidate.getExpectedCtc()!=null)
			existingcandidate.setExpectedCtc(candidate.getExpectedCtc());
		if(candidate.getIsOnNoticePeriod()!=null)
			existingcandidate.setIsOnNoticePeriod(candidate.getIsOnNoticePeriod());
		if(candidate.getLinkedinLink()!=null)
			existingcandidate.setLinkedinLink(candidate.getLinkedinLink());
		if(candidate.getMobileNo()!=null)
			existingcandidate.setMobileNo(candidate.getMobileNo());
		if(candidate.getNoticePeriod()!=null)
			existingcandidate.setNoticePeriod(candidate.getNoticePeriod());
		if(candidate.getPrimarySkill()!=null)
			existingcandidate.setPrimarySkill(candidate.getPrimarySkill());
		if(candidate.getResume()!=null)
			existingcandidate.setResume(candidate.getResume());
		if(candidate.getSecondarySkill()!=null)
			existingcandidate.setSecondarySkill(candidate.getSecondarySkill());
		if(candidate.getTotalExp()!=null)
			existingcandidate.setTotalExp(candidate.getTotalExp());
		candidateDetailsRepository.save(existingcandidate);
		
		return null;
	}

	@Override
	public String changeCandidateStatus(long candidId, String status) {
		CandidateDetails cand=candidateDetailsRepository.findById(candidId).get();
		cand.setStatus(status);
		candidateDetailsRepository.save(cand);
		return "status changed";
	}

	@Override
	public List<String> getAllRole() {
		return candidateDetailsRepository.getAllRole();
	}

}
