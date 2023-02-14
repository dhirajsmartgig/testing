package com.smartgigInternal.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.smartgigInternal.dto.EmployeeDTO;
import com.smartgigInternal.dto.EmployeeLeaveDto;
import com.smartgigInternal.dto.Hike;
import com.smartgigInternal.entity.DocumentDetails;
import com.smartgigInternal.entity.EmployeeDetails;

public interface EmployeeDetailsService {

	public Map<String, String> saveEmployeeDetails(EmployeeDetails employeeDetails);

	public Map<String, Object> getEmployeeDetails(Long id);

	public String changeStatusOfEmployee(Long id);

	public Map<String, Object> editEmployeeDetails(EmployeeDTO dto, Long id);

	List<Map<String, Object>> getAllEmployeeByStatus();

	public String saveEmployeeDocuments(DocumentDetails documnets);

	public DocumentDetails findByEmployeeDetailsId(long id);

	public boolean existsByEmail(String email);

	public boolean existsByMobileNo(String mobileNo);

	List<EmployeeDetails> getTechnicalEmp();

	public DocumentDetails getDocs(Long employeeDetailsId);

	public List<EmployeeDetails> getCarsBySpecs(EmployeeDetails carSpecs);
  
	public List<Map<String, String>>  saveBulkEmployeeDetails(MultipartFile bulkUploadExcel);
	
	public List<String> getAllSkill();

	List<EmployeeDetails> getByrollandfullandcurrentlocationAndstatus(String fullname, String role,
			String currentlocation, boolean status);

	public String empHike(Hike hike);

	public List<Hike> getHikeInfoByEmpId(long empId);

	Set<EmployeeDetails> hikeEligibility() throws ParseException;

	List<Map<String, Object>> getAllEmployeeLeaves();

	Map<String, Object> getEmployeeLeaveById(Long id);

	public String empLeaveApply(EmployeeLeaveDto employeeLeaveDto);

	List<Map<String, Object>> getEmployeeLeaveHistoryById(Long id);
	
	

}
