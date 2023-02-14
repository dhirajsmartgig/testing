package com.smartgigInternal.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartgigInternal.dto.Hike;

public interface HikeRepository extends JpaRepository<Hike, Long> {

	List<Hike> findByEmployeeDetailsId(long empId);

	List<Hike> findByEffectiveDate(Date date);

	@Query(value = "select eg from Hike eg where month(eg.effectiveDate)=?1 and year(eg.effectiveDate)=?2 ")
	Set<Hike> findByMonth(int month, int year);

	@Query(value = "select eg.employeeDetailsId from Hike eg")
	List<Long> getEmpId();

}