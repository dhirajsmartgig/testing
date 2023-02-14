package com.smartgigInternal.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BillingRateRepository extends JpaRepository<BillingRate, Long> {

	@Query(value = "select eg from BillingRate eg where eg.employeeDetailsId=?1 and eg.clientId=?2 and eg.projectId=?3 and month(eg.toDate)=?4")
	BillingRate findByEmployeeDetailsIdAndClientIdAndProjectIdAndMonth(Long employeeDetailsId, Long clientId,
			Long projectId, int month);

	List<BillingRate> findByEmployeeDetailsId(Long employeeDetailsId);
	
	List<BillingRate> findByProjectId(long projectId);

}
