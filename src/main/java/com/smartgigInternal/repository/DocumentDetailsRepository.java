package com.smartgigInternal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartgigInternal.entity.DocumentDetails;

public interface DocumentDetailsRepository extends JpaRepository<DocumentDetails, Long> {
	
	@Query(value = "select eg from DocumentDetails eg where eg.employeeDetailsId =?1")
	DocumentDetails findByEmployeeDetailsId(Long id);

}
