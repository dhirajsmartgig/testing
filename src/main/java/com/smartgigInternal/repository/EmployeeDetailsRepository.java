package com.smartgigInternal.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartgigInternal.entity.EmployeeDetails;

public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {

	EmployeeDetails findByEmployeeDetailsId(Long id);

	List<EmployeeDetails> findByStatus(boolean status);

	public boolean existsByEmail(String email);

	public boolean existsByMobileNo(String mobileNo);

	public boolean existsByFullNameAndStatus(String name, boolean status);

	@Query(value = "select eg from EmployeeDetails eg where eg.employeeDetailsId=(select max(eg.employeeDetailsId) from EmployeeDetails eg)")
	EmployeeDetails getLatestEmp();

	@Query(value = "select eg from EmployeeDetails eg where eg.status=true")
	List<EmployeeDetails> getTechnicalEmp();

	EmployeeDetails findByFullNameAndStatus(String name, boolean status);

	@Query(value = "select eg.fullName from EmployeeDetails eg where eg.status=true")
	List<String> getAllEmployee();

	@Query(value = "select eg from EmployeeDetails eg where eg.email=?1 and eg.status=?2")
	EmployeeDetails findByEmailAndStatus(String username, boolean b);

	@Query(value = "select eg from EmployeeDetails eg where eg.accessRole In('SUPER_ADMIN','ADMIN') and eg.status=?1")
	List<EmployeeDetails> findByAccessRoleInAndStatus(boolean b);

	// searching methods;
	List<EmployeeDetails> findByStatusAndFullName(boolean b, String fullname);

	List<EmployeeDetails> findByStatusAndRole(boolean b, String fullname);

	// List<EmployeeDetails> findByStatusAndstatus(boolean b, boolean status);

	List<EmployeeDetails> findByStatusAndPrimaryLocation(boolean b, String currentlocation);

	EmployeeDetails findByFullName(String fullName);

	List<EmployeeDetails> findAllByRole(String role);

	List<EmployeeDetails> findByRoleAndStatus(String role, boolean b);

	@Query(value = "Select skill from employee_skill ", nativeQuery = true)
	List<String> getAllSkill();

	List<EmployeeDetails> findByStatusAndRoleAndPrimaryLocation(boolean status, String role, String currentlocation);

	@Query(value = "select eg from EmployeeDetails eg where eg.dateOfJoining>=?1 and eg.dateOfJoining<=?2")
	List<EmployeeDetails> findEmployeeDetailsIdByDateBetween(Date fromDate, Date toDate);

	@Query(value = "select eg from EmployeeDetails eg where eg.dateOfJoining=?1")
	List<EmployeeDetails> findByDateOfJoining(Date localDate);

	@Query(value = "select eg from EmployeeDetails eg where eg.status=true and month(eg.dateOfJoining)=?1 and year(eg.dateOfJoining)=?2 and eg.employeeDetailsId not in(?3)")
	Set<EmployeeDetails> findByEligible(int month, int year, List<Long> list);

	EmployeeDetails findByEmployeeDetailsIdAndStatus(Long employeeDetailsId, boolean b);

//    List<EmployeeDetails> findByDateOfJoining();

	List<EmployeeDetails> findByLastWorkingDay(Date date);

}
