package com.smartgigInternal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgigInternal.dto.LeaveDetails;

public interface LeaveDetailsRepository extends JpaRepository<LeaveDetails, Integer> {

	List<LeaveDetails> findAllByEmployeeDetailsId(Long id);

}
