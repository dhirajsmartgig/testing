package com.smartgigInternal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartgigInternal.entity.CandidateDetails;
import com.smartgigInternal.entity.DocumentDetails;

public interface CandidateDetailsRepository extends JpaRepository<CandidateDetails, Long> {

	List<CandidateDetails> findByStatus(String status);
      
	@Query( value = "Select role from roleAndDesignation ", nativeQuery = true)
	List<String> getAllRole();

	CandidateDetails findByIdAndStatus(Long id,String string);

	List<CandidateDetails> findByAppliedFor(String role);

	List<CandidateDetails> findByAppliedForAndStatus(String role, String status);
	
  // public DocumentDetails findByemployeeDetailsId(Long employeeDetailsId);

}
