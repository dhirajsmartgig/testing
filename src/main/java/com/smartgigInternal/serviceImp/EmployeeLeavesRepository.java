package com.smartgigInternal.serviceImp;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartgigInternal.entity.EmployeeLeaves;

public interface EmployeeLeavesRepository extends JpaRepository<EmployeeLeaves, Integer> {

	EmployeeLeaves findByEmployeeDetailsId(Long employeeDetailsId);

}
