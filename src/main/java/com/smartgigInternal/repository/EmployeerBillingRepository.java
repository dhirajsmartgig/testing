package com.smartgigInternal.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartgigInternal.entity.EmployeeBilling;
import com.smartgigInternal.entity.ProjectDetails;

public interface EmployeerBillingRepository extends JpaRepository<EmployeeBilling, Long> {

	@Query(value = "select eg from EmployeeBilling eg where eg.employeeDetailsId=?1 and year(eg.fromDate)=?2 and year(eg.toDate)=?2")
	List<EmployeeBilling> findByEmployeeDetailsId(Long employeeDetailsId, Integer year);

	@Query(value = "select sum(eg.totalAmount) from EmployeeBilling eg where eg.clientId=?1 and eg.fromDate>=?2 and eg.toDate<=?3")
	Long getTotalAmountByClientIdAndDateBetween(Long clientId, Date fromDate, Date toDate);

	@Query(value = "select count(distinct(employee_details_id))from employee_billing  where client_id=:clientId and from_date>=:fromDate and to_date<=:toDate", nativeQuery = true)
	Long getTotalEmpByClientIdAndDateBetween(Long clientId, Date fromDate, Date toDate);

	@Query(value = "select count(distinct(employee_details_id))from employee_billing  where client_id=:clientId and project_id=:projectId and from_date>=:fromDate and to_date<=:toDate", nativeQuery = true)
	Long getTotalEmpByClientIdAndProjectIdAndDateBetween(Long clientId, Long projectId, Date fromDate, Date toDate);

	@Query(value = "select sum(eg.totalAmount) from EmployeeBilling eg where eg.clientId=?1 and eg.projectId=?2 and eg.fromDate>=?3 and eg.toDate<=?4")
	Long getTotalAmountByClientIdAndProjectIdAndDateBetween(Long clientId, Long projectId, Date fromDate, Date toDate);

	@Query(value = "select eg.projectId from EmployeeBilling eg where eg.clientId=?1")
	Set<Long> finByClientId(Long clientId);

	@Query(value = "select eg from EmployeeBilling eg where eg.employeeDetailsId=?1 and eg.clientId=?2 and eg.projectId=?3 and month(eg.toDate)=?4")
	EmployeeBilling findByEmployeeDetailsIdAndClientIdAndProjectIdAndMonth(Long employeeDetailsId, Long clientId, Long projectId,
			int month);
      
	List<EmployeeBilling> findByEmployeeDetailsId(Long employeeDetailsId);

	List<EmployeeBilling> findByClientId(Long clientId);

	List<EmployeeBilling> findByProjectId(Long projectId);
	
//	List<EmployeeBilling> findAllByFromtDateLessThanEqualAndToDateGreaterThanEqual(Example<EmployeeBilling>t,Date fromDate, Date toDate);
	
	@Query( value = "SELECT * from employee_billing WHERE from_date<=:fromDate and to_date>=:toDate", nativeQuery = true)
    public List<EmployeeBilling> findBetweenFromAndToDate(Example<EmployeeBilling>t,Date fromDate, Date toDate);

	//List<EmployeeBilling> getBetweenFromAndToDate(Date of, Date fromDate, Date toDate);
	
	@Query(value = "select sum (eg.totalAmount) from EmployeeBilling eg where eg.employeeDetailsId=?1 and year(eg.fromDate)=?2 and year(eg.toDate)=?2")
    double findBysum(Long employeeDetailsId, Integer year);
}
