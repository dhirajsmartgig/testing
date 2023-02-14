package com.smartgigInternal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartgigInternal.entity.ProjectEmployee;

public interface EmployeeProjectRepository extends JpaRepository<ProjectEmployee, Long> {

	Optional<ProjectEmployee> findByProjectIdAndEmployeeDetailsId(Long projectId, Long employeeDetailsId);

	List<ProjectEmployee> findByStatus(boolean status);

	List<ProjectEmployee> findByEmployeeDetailsId(Long employeeDetailsId);

	List<ProjectEmployee> findByEmployeeDetailsIdAndStatus(Long projectEmployeeId, boolean b);

	ProjectEmployee findByEmployeeDetailsIdAndClientIdAndProjectIdAndStatus(Long employeeDetailsId, Long clientId,
			Long projectId, boolean b);

	ProjectEmployee findByProjectEmployeeId(Long projectEmployeeId);


	List<ProjectEmployee>findByClientIdAndStatus(Long clientId,boolean b);

	List<ProjectEmployee> findByProjectIdAndStatus(Long projectId, boolean b);

	//Optional<ProjectEmployee> findByProjectIdAndEmployeeDetailsId(Long projectId, Set<Long> employeeDetailsId);>>>>>>> 3136c77ede28dfda07ed8d9785646c4a87cee32d

	// Optional<ProjectEmployee> findByProjectIdAndEmployeeDetailsId(Long projectId,
	// Set<Long> employeeDetailsId);

}