package com.smartgigInternal.serviceImp;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartgigInternal.dto.EmployeeDTO;
import com.smartgigInternal.dto.EmployeeLeaveDto;
import com.smartgigInternal.dto.Hike;
import com.smartgigInternal.dto.LeaveDetails;
import com.smartgigInternal.entity.BillingRate;
import com.smartgigInternal.entity.BillingRateRepository;
import com.smartgigInternal.entity.DocumentDetails;
import com.smartgigInternal.entity.EmployeeDetails;
import com.smartgigInternal.entity.EmployeeLeaves;
import com.smartgigInternal.entity.ProjectEmployee;
import com.smartgigInternal.repository.DocumentDetailsRepository;
import com.smartgigInternal.repository.EmployeeDetailsRepository;
import com.smartgigInternal.repository.EmployeeProjectRepository;
import com.smartgigInternal.repository.HikeRepository;
import com.smartgigInternal.repository.LaptopRepository;
import com.smartgigInternal.repository.LeaveDetailsRepository;
import com.smartgigInternal.service.EmployeeDetailsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {

	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	private DocumentDetailsRepository documentationRepo;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private LaptopRepository laptopRepo;

	@Autowired
	private HikeRepository hikeRepo;

	@Autowired
	private EmployeeProjectRepository employeeProjectRepository;

	@Autowired
	private EmployeeLeavesRepository employeeLeavesRepository;

	@Autowired
	private LeaveDetailsRepository leaveDetailsRepository;

	@Autowired
	private BillingRateRepository billingRateRepository;

	@Override
	@Transactional
	public Map<String, String> saveEmployeeDetails(EmployeeDetails employeeDetails) {
		Map<String, String> map = new HashMap<>();
		EmployeeDetails emp = this.employeeDetailsRepository.getLatestEmp();
		String empId = null;
		if (emp == null) {
			empId = "SG21001";
		} else {
			empId = emp.getEmpId();
			int id = Integer.parseInt(empId.substring(4)) + 1;
			if (id < 10) {
				empId = "SG2100" + id;
			} else if (id < 100) {
				empId = "SG210" + id;
			} else {
				empId = "SG21" + id;
			}
		}
		employeeDetails.setEmpId(empId);
		employeeDetails.setPassword(bcrypt.encode("smartgig@123"));
		employeeDetails.setAccessRole("Employee");

		if (this.employeeDetailsRepository.existsByFullNameAndStatus(employeeDetails.getFullName(), true)) {
			map.put("msg", "this name already presnt");
		} else if (employeeDetailsRepository.existsByEmail(employeeDetails.getEmail())) {
			map.put("msg", "this email id already present");
		} else if (employeeDetailsRepository.existsByMobileNo(employeeDetails.getMobileNo()) == true) {
			map.put("msg", "this mobile number already present");
		} else {
			EmployeeDetails savedEmployeeDetails = this.employeeDetailsRepository.save(employeeDetails);
			EmployeeLeaves employeeLeaves = new EmployeeLeaves();
			employeeLeaves.setEmployeeDetailsId(savedEmployeeDetails.getEmployeeDetailsId());
			employeeLeavesRepository.save(employeeLeaves);
			if (savedEmployeeDetails != null) {
				map.put("msg", "data saved");
				map.put("empId", employeeDetails.getEmployeeDetailsId() + "");
			} else {
				map.put("msg", "Didn't saved");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> getEmployeeDetails(Long id) {
		EmployeeDetails emp = employeeDetailsRepository.findByEmployeeDetailsId(id);
		Map<String, Object> map = new HashMap<>();
		map.put("employeeDetailsId", emp.getEmployeeDetailsId());
		map.put("empId", emp.getEmpId());
		map.put("fullName", emp.getFullName());
		map.put("gender", emp.getGender());
		if (emp.getDateOfJoining() != null) {
			map.put("dateOfJoining", emp.getDateOfJoining().toString());
		} else {
			map.put("dateOfJoining", null);
		}
		map.put("primaryLocation", emp.getPrimaryLocation());
		map.put("currentLocation", emp.getCurrentLocation());
		map.put("qualification", emp.getQualification());
		map.put("primarySkills", emp.getPrimarySkills());
		map.put("secondrySkills", emp.getSecondrySkills());
		map.put("dateOfBirth", emp.getDateOfBirth().toString());
		if (emp.getDateOfMarriage() != null) {
			map.put("dateOfMarriage", emp.getDateOfMarriage().toString());
		} else {
			map.put("dateOfMarriage", null);
		}
		map.put("ctc", emp.getCtc());
		map.put("degination", emp.getDegination());
		map.put("empType", emp.getEmpType());
		map.put("status", emp.isStatus());
		if (emp.getLastWorkingDay() != null) {
			map.put("lastWorkingDay", emp.getLastWorkingDay().toString());
		} else {
			map.put("lastWorkingDay", null);
		}
		map.put("email", emp.getEmail());
		map.put("mobileNo", emp.getMobileNo());
		map.put("role", emp.getRole());
		map.put("exp", emp.getExp());
		map.put("fixedCtc", emp.getFixedCtc());
		map.put("variablePay", emp.getVariablePay());
		map.put("hiredAs", emp.getHiredAs());
		map.put("alternateNo", emp.getAlternateMobile());
		map.put("uan", emp.getUAN());
		return map;
	}

	@Override
	public String changeStatusOfEmployee(Long id) {
		EmployeeDetails employee = employeeDetailsRepository.findByEmployeeDetailsId(id);
		employee.setStatus(false);
		this.employeeDetailsRepository.save(employee);
		return "deleted";
	}

	@Override
	public Map<String, Object> editEmployeeDetails(EmployeeDTO dto, Long id) {
		EmployeeDetails exsistingEmployeeDetails = employeeDetailsRepository.findByEmployeeDetailsId(id);
		Map<String, Object> map = new HashMap<>();
		if (exsistingEmployeeDetails != null) {
				exsistingEmployeeDetails.setFullName(dto.getFullName());
				exsistingEmployeeDetails.setGender(dto.getGender());
				exsistingEmployeeDetails.setDateOfBirth(dto.getDateOfBirth());
				exsistingEmployeeDetails.setCtc(dto.getCtc());
				exsistingEmployeeDetails.setDateOfJoining(dto.getDateOfJoining());
				exsistingEmployeeDetails.setDateOfMarriage(dto.getDateOfMarriage());
				exsistingEmployeeDetails.setDegination(dto.getDegination());
				exsistingEmployeeDetails.setEmpType(dto.getEmpType());
				exsistingEmployeeDetails.setPrimaryLocation(dto.getPrimaryLocation());
				exsistingEmployeeDetails.setCurrentLocation(dto.getCurrentLocation());
				exsistingEmployeeDetails.setPrimarySkills(dto.getPrimarySkills());
				exsistingEmployeeDetails.setSecondrySkills(dto.getSecondrySkills());
				exsistingEmployeeDetails.setQualification(dto.getQualification());
				exsistingEmployeeDetails.setEmail(dto.getEmail());
				exsistingEmployeeDetails.setMobileNo(dto.getMobileNo());
				exsistingEmployeeDetails.setRole(dto.getRole());
				exsistingEmployeeDetails.setFixedCtc(dto.getFixedCtc());
				exsistingEmployeeDetails.setVariablePay(dto.getVariablePay());
				exsistingEmployeeDetails.setExp(dto.getExp());
				exsistingEmployeeDetails.setLastWorkingDay(dto.getLastWorkingDay());
				exsistingEmployeeDetails.setAlternateMobile(dto.getAlternateMobile());
				exsistingEmployeeDetails.setUAN(dto.getUAN());
				exsistingEmployeeDetails.setHiredAs(dto.getHiredAs());

			EmployeeDetails emp = employeeDetailsRepository.save(exsistingEmployeeDetails);
			if (emp.getLastWorkingDay().before(new Date())) {
				List<ProjectEmployee> project = employeeProjectRepository
						.findByEmployeeDetailsIdAndStatus(emp.getEmployeeDetailsId(), true);
				if (project.isEmpty()) {
					project.forEach(u -> {
						u.setStatus(false);
						this.employeeProjectRepository.save(u);
					});
				}
				emp.setStatus(false);
				employeeDetailsRepository.save(emp);
			}
			map.put("msg", "data updated successfully");
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getAllEmployeeByStatus() {
		List<EmployeeDetails> list = employeeDetailsRepository.findByStatus(true);
		List<Map<String, Object>> responseList = new ArrayList<>();
		for (EmployeeDetails emp : list) {
			Map<String, Object> map = new HashMap<>();
			map.put("employeeDetailsId", emp.getEmployeeDetailsId());
			map.put("empId", emp.getEmpId());
			map.put("fullName", emp.getFullName());
			map.put("gender", emp.getGender());
			map.put("dateOfJoining", emp.getDateOfJoining().toString());
			map.put("primaryLocation", emp.getPrimaryLocation());
			map.put("currentLocation", emp.getCurrentLocation());
			map.put("qualification", emp.getQualification());
			map.put("primarySkills", emp.getPrimarySkills());
			map.put("secondrySkills", emp.getSecondrySkills());
			map.put("dateOfBirth", emp.getDateOfBirth().toString());
			if (emp.getDateOfMarriage() != null) {
				map.put("dateOfMarriage", emp.getDateOfMarriage().toString());
			} else {
				map.put("dateOfMarriage", null);
			}

			map.put("degination", emp.getDegination());
			map.put("empType", emp.getEmpType());
			map.put("status", emp.isStatus());
			if (emp.getLastWorkingDay() != null) {
				map.put("lastWorkingDay", emp.getLastWorkingDay().toString());
			} else {
				map.put("lastWorkingDay", null);
			}
			map.put("email", emp.getEmail());
			map.put("mobileNo", emp.getMobileNo());
			map.put("role", emp.getRole());
			map.put("exp", emp.getExp());
			map.put("fixedCtc", emp.getFixedCtc());
			map.put("variablePay", emp.getVariablePay());
			map.put("ctc", emp.getCtc());
			map.put("hiredAs", emp.getHiredAs());

			// adding employeeBilling in getAllEmployeeByStatus
			List<BillingRate> empBillings = billingRateRepository.findByEmployeeDetailsId(emp.getEmployeeDetailsId());
			for (BillingRate empBilling : empBillings) {
				if (empBilling != null) {
					map.put("Rate", empBilling.getRate());
					map.put("RateType", empBilling.getRateType());
				}
			}

			if (emp.getAlternateMobile() != null) {
				map.put("alternateNo", emp.getAlternateMobile());
			} else {
				map.put("alternateNo", "");
			}
			if (emp.getUAN() != null) {
				map.put("uan", emp.getUAN());
			} else {
				map.put("uan", "");
			}
			responseList.add(map);
		}
		return responseList;
	}

	@Override
	public String saveEmployeeDocuments(DocumentDetails documnets) {
		this.documentationRepo.save(documnets);
		return "saved";
	}

	@Override
	public DocumentDetails findByEmployeeDetailsId(long id) {
		return documentationRepo.findByEmployeeDetailsId(id);
	}

	@Override
	public boolean existsByEmail(String email) {
		return employeeDetailsRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByMobileNo(String mobileNo) {
		return employeeDetailsRepository.existsByMobileNo(mobileNo);
	}

	@Override
	public List<EmployeeDetails> getTechnicalEmp() {
		return this.employeeDetailsRepository.getTechnicalEmp();
	}

	@Override
	public DocumentDetails getDocs(Long employeeDetailsId) {
		return this.documentationRepo.findByEmployeeDetailsId(employeeDetailsId);
	}

	@Override
	public List<EmployeeDetails> getByrollandfullandcurrentlocationAndstatus(String fullname, String role,
			String currentlocation, boolean status) {
		if (fullname != "") {
			return this.employeeDetailsRepository.findByStatusAndFullName(status, fullname);

		} else if (role != "" && currentlocation == "") {
			return this.employeeDetailsRepository.findByStatusAndRole(status, role);

		} else if (role == "" && currentlocation != "") {
			return this.employeeDetailsRepository.findByStatusAndPrimaryLocation(status, currentlocation);

		} else if (role != "" && currentlocation != "") {
			return this.employeeDetailsRepository.findByStatusAndRoleAndPrimaryLocation(status, role, currentlocation);
		} else if (role == "" && currentlocation == "") {
			return this.employeeDetailsRepository.findByStatus(status);
		}
		return null;
	}

	@Override
	public List<EmployeeDetails> getCarsBySpecs(EmployeeDetails carSpecs) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("employeeDetailsId").withIgnorePaths("empId")
				.withIgnorePaths("gender").withIgnorePaths("dateOfJoining").withIgnorePaths("currentLocation")
				.withIgnorePaths("qualification").withIgnorePaths("primarySkills").withIgnorePaths("secondrySkills")
				.withIgnorePaths("dateOfBirth").withIgnorePaths("dateOfMarriage").withIgnorePaths("ctc")
				.withIgnorePaths("degination").withIgnorePaths("empType").withIgnorePaths("status")
				.withIgnorePaths("lastWorkingDay").withIgnorePaths("email").withIgnorePaths("mobileNo")
				.withIgnorePaths("accessRole").withIgnorePaths("changePassword").withIgnorePaths("exp")
				.withIgnorePaths("fixedCtc").withIgnorePaths("variablePay");
		return employeeDetailsRepository.findAll(Example.of(carSpecs, matcher));
	}

	public List<Map<String, String>> saveBulkEmployeeDetails(MultipartFile bulkUploadExcel) {
		List<Map<String, String>> list = new ArrayList<>();
		try {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(bulkUploadExcel.getInputStream());
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			Row row;
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");

			for (int i = 1; i < xssfSheet.getLastRowNum(); i++) {
				row = xssfSheet.getRow(i);
				String fullName = row.getCell(0).getStringCellValue();
				String gender = row.getCell(1).getStringCellValue();
				String currentLocation = row.getCell(3).getStringCellValue();
				String role = row.getCell(4).getStringCellValue();
				String primaryLocation = row.getCell(5).getStringCellValue();
				String qualification = row.getCell(6).getStringCellValue();
				String primarySkill = row.getCell(7).getStringCellValue();
				String secondarySkill = row.getCell(8).getStringCellValue();
				String format;
				if (row.getCell(9) != null) {
					Date dateOfBirth = row.getCell(9).getDateCellValue();// date
					format = dt1.format(dateOfBirth);
				} else {
					format = null;
				}
//				String dateOfMarriageStr = null;
//				if (row.getCell(10)!= null) {
//					Date dateOfMarriage = row.getCell(10).getDateCellValue();// date
//					dateOfMarriageStr = dt1.format(dateOfMarriage);
//				} 
				String dateOfJoinStr;
				if (row.getCell(2) != null) {
					Date dateOfJoin = row.getCell(2).getDateCellValue();// date
					dateOfJoinStr = dt1.format(dateOfJoin);
				} else {
					dateOfJoinStr = null;
				}
				double ctc = row.getCell(11).getNumericCellValue();// double value
				String designation = row.getCell(12).getStringCellValue();
				String empType = row.getCell(13).getStringCellValue();
				String email = row.getCell(14).getStringCellValue();
				String mobile = row.getCell(15).toString();
				double experience = row.getCell(16).getNumericCellValue();// double
				double fixedCtc = row.getCell(17).getNumericCellValue();// double
				double variablePay = row.getCell(18).getNumericCellValue();// double

				Map<String, String> map = new HashMap<>();

				EmployeeDetails emp = this.employeeDetailsRepository.getLatestEmp();
				String empId = null;
				if (emp == null) {
					empId = "SG21001";
				} else {
					empId = emp.getEmpId();
					int id = Integer.parseInt(empId.substring(4)) + 1;
					if (id < 10) {
						empId = "SG2100" + id;
					} else if (id < 100) {
						empId = "SG210" + id;
					} else {
						empId = "SG21" + id;
					}
				}
				EmployeeDetails employeeDetails = new EmployeeDetails();

				employeeDetails.setFullName(fullName);
				employeeDetails.setGender(gender);
				employeeDetails.setRole(role);
				employeeDetails.setPrimaryLocation(primaryLocation);
				employeeDetails.setQualification(qualification);
				employeeDetails.setPrimarySkills(primarySkill);
				employeeDetails.setSecondrySkills(secondarySkill);
				employeeDetails.setCtc(ctc);
				employeeDetails.setDegination(designation);
				employeeDetails.setEmpType(empType);
				employeeDetails.setEmail(email);
				employeeDetails.setMobileNo(mobile);
				employeeDetails.setStatus(true);
				employeeDetails.setExp(experience);
				employeeDetails.setFixedCtc(fixedCtc);
				employeeDetails.setVariablePay(variablePay);
				employeeDetails.setCurrentLocation(currentLocation);
				try {
					if (format == null) {
						employeeDetails.setDateOfBirth(null);
					} else {
						employeeDetails.setDateOfBirth(dt1.parse(format));
					}

//					if (dateOfMarriageStr == null) {
//						employeeDetails.setDateOfMarriage(null);
//					} else {
//						employeeDetails.setDateOfMarriage(dt1.parse(dateOfMarriageStr));
//					}

					if (dateOfJoinStr == null) {
						employeeDetails.setDateOfJoining(null);
					} else {
						employeeDetails.setDateOfJoining(dt1.parse(dateOfJoinStr));
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}
				employeeDetails.setEmpId(empId);
				employeeDetails.setPassword(bcrypt.encode("smartgig@123"));
				employeeDetails.setAccessRole("Employee");

				if (this.employeeDetailsRepository.existsByFullNameAndStatus(employeeDetails.getFullName(), true)) {
					map.put("empName", employeeDetails.getFullName());
				} else if (employeeDetailsRepository.existsByEmail(employeeDetails.getEmail())) {
					map.put("empName", employeeDetails.getFullName());
				} else if (employeeDetailsRepository.existsByMobileNo(employeeDetails.getMobileNo()) == true) {
					map.put("empName", employeeDetails.getFullName());
				} else {

					EmployeeDetails savedEmployeeDetails = this.employeeDetailsRepository.save(employeeDetails);
					EmployeeLeaves employeeLeaves = new EmployeeLeaves();
					employeeLeaves.setEmployeeDetailsId(savedEmployeeDetails.getEmployeeDetailsId());
					employeeLeavesRepository.save(employeeLeaves);

					if (savedEmployeeDetails == null) {
						map.put("empName", employeeDetails.getFullName());
					}
				}
				if (!map.isEmpty()) {
					list.add(map);
				}
			}
			xssfWorkbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<String> getAllSkill() {
		return employeeDetailsRepository.getAllSkill();
	}

	@Override
	@Transactional
	public String empHike(Hike hike) {
		try {
			hike.setHikeAmount(hike.getHikeAmount());
			hike.setHikeVariablePay(hike.getHikeVariablePay());
			hike.setTotal_ctc(hike.getHikeAmount() + hike.getHikeVariablePay());
			hike.setUpdatedOn(new Date());
			hikeRepo.save(hike);
			EmployeeDetails emp = employeeDetailsRepository.findByEmployeeDetailsId(hike.getEmployeeDetailsId());
//		if (!(emp.getFixedCtc() == emp.getFixedCtc()) && !(emp.getVariablePay() == emp.getVariablePay())) {
			emp.setFixedCtc(emp.getFixedCtc() + hike.getHikeAmount());
			emp.setVariablePay(emp.getVariablePay() + hike.getHikeVariablePay());
			emp.setCtc((emp.getFixedCtc() + hike.getHikeAmount() + emp.getVariablePay() + hike.getHikeVariablePay())
					- 1000);
			employeeDetailsRepository.save(emp);
//		}
			return "data captured!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Something went wrong!";
		}
	}

	@Override
	public List<Hike> getHikeInfoByEmpId(long empId) {
		return hikeRepo.findByEmployeeDetailsId(empId);
	}

	@Scheduled(cron = "0 0 * * * *")
	void hikeEffectiveCronJob() {
		List<Hike> hikeList = hikeRepo.findByEffectiveDate(new Date());
		for (Hike hike : hikeList) {
			EmployeeDetails emp = employeeDetailsRepository.findByEmployeeDetailsId(hike.getEmployeeDetailsId());
			emp.setCtc(emp.getCtc() + hike.getHikeAmount());
			employeeDetailsRepository.save(emp);
		}
	}

	@Scheduled(cron = "0 0 23 * * *")
	void autoInactive() {
		List<EmployeeDetails> list = employeeDetailsRepository.findByLastWorkingDay(new Date());
		for (EmployeeDetails empd : list) {
			EmployeeDetails emp = employeeDetailsRepository.findByEmployeeDetailsId(empd.getEmployeeDetailsId());
			List<ProjectEmployee> project = employeeProjectRepository
					.findByEmployeeDetailsIdAndStatus(empd.getEmployeeDetailsId(), true);
			if (project.isEmpty()) {
				project.forEach(u -> {
					u.setStatus(false);
					this.employeeProjectRepository.save(u);
				});
			}
			emp.setStatus(false);
			employeeDetailsRepository.save(emp);
		}
	}

	@Override
	public Set<EmployeeDetails> hikeEligibility() throws ParseException {
		LocalDate minusMonth = new LocalDate().minusMonths(11);
		Set<EmployeeDetails> setResponse = new HashSet<>();
		List<Long> list = this.hikeRepo.getEmpId();
		if (list.isEmpty())
			list.add(0L);
		Set<Hike> hikeList = this.hikeRepo.findByMonth(minusMonth.getMonthOfYear(), minusMonth.getYear());
		Set<EmployeeDetails> set = this.employeeDetailsRepository.findByEligible(minusMonth.getMonthOfYear(),
				minusMonth.getYear(), list);
		for (Hike hike : hikeList) {
			EmployeeDetails emp = this.employeeDetailsRepository
					.findByEmployeeDetailsIdAndStatus(hike.getEmployeeDetailsId(), true);
			setResponse.add(emp);

		}
		setResponse.addAll(set);
		return setResponse;
	}

	@Override
	public List<Map<String, Object>> getAllEmployeeLeaves() {
		List<Map<String, Object>> list = new ArrayList<>();
//		List<EmployeeDetails> emps = employeeDetailsRepository.findByStatus(true);
		EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeDetailsId(620l);
//		for (EmployeeDetails employeeDetails : emps) {
		Map<String, Object> map = new HashMap<>();
		EmployeeLeaves empLeave = employeeLeavesRepository
				.findByEmployeeDetailsId(employeeDetails.getEmployeeDetailsId());

		EmployeeDetails employee = employeeDetailsRepository
				.findByEmployeeDetailsId(employeeDetails.getEmployeeDetailsId());

		map.put("empId", empLeave.getEmployeeDetailsId());
		map.put("fullName", employee.getFullName());
		map.put("casualAvailable", empLeave.getCasualLeaveAvailable());
		map.put("casualAvailed", empLeave.getCasualLeaveAvailed());
		map.put("sickAvailable", empLeave.getSickLeaveAvailable());
		map.put("sickAvailed", empLeave.getSickLeaveAvailed());
		map.put("totalPaidLeavesAvailable", empLeave.getTotalPaidLeavesAvailable());
		map.put("totalPaidLeavesAvailed", empLeave.getTotalPaidLeavesAvailed());
		map.put("lop", empLeave.getLOP());
		list.add(map);

//		}
		return list;
	}

	@Override
	public Map<String, Object> getEmployeeLeaveById(Long id) {

		EmployeeLeaves empLeave = employeeLeavesRepository.findByEmployeeDetailsId(id);
		EmployeeDetails employee = employeeDetailsRepository.findByEmployeeDetailsId(id);

		Map<String, Object> map = new HashMap<>();

		map.put("empId", empLeave.getEmployeeDetailsId());
		map.put("fullName", employee.getFullName());
		map.put("casualAvailable", empLeave.getCasualLeaveAvailable());
		map.put("casualAvailed", empLeave.getCasualLeaveAvailed());
		map.put("sickAvailable", empLeave.getSickLeaveAvailable());
		map.put("sickAvailed", empLeave.getSickLeaveAvailed());
		map.put("totalPaidLeavesAvailable", empLeave.getTotalPaidLeavesAvailable());
		map.put("totalPaidLeavesAvailed", empLeave.getTotalPaidLeavesAvailed());
		map.put("lop", empLeave.getLOP());
		return map;
	}

	@Override
	public String empLeaveApply(EmployeeLeaveDto employeeLeaveDto) {
		EmployeeLeaves employeeLeave = employeeLeavesRepository
				.findByEmployeeDetailsId(employeeLeaveDto.getEmployeeDetailsId());

		LeaveDetails leaveDetails = new LeaveDetails();

		double casualLeaveAvailable = employeeLeave.getCasualLeaveAvailable();
		double casualLeaveAvailed = employeeLeave.getCasualLeaveAvailed();
		double sickLeaveAvailable = employeeLeave.getSickLeaveAvailable();
		double sickLeaveAvailed = employeeLeave.getSickLeaveAvailed();
		double totalPaidLeavesAvailable = employeeLeave.getTotalPaidLeavesAvailable();
		double totalPaidLeavesAvailed = employeeLeave.getTotalPaidLeavesAvailed();

		if (employeeLeaveDto.getLeaveType().equalsIgnoreCase("casual")) {
			// casual leave apply
			if (casualLeaveAvailable != 0) {
				double casualLeave = casualLeaveAvailable - employeeLeaveDto.getTotalDays();
				employeeLeave.setCasualLeaveAvailable(casualLeave);
				employeeLeave.setCasualLeaveAvailed(casualLeaveAvailed + employeeLeaveDto.getTotalDays());
				employeeLeave.setTotalPaidLeavesAvailable(totalPaidLeavesAvailable - employeeLeaveDto.getTotalDays());
				employeeLeave.setTotalPaidLeavesAvailed(totalPaidLeavesAvailed + employeeLeaveDto.getTotalDays());
				leaveDetails.setLeaveType(employeeLeaveDto.getLeaveType());
			} else {
				employeeLeave.setLOP(employeeLeave.getLOP() + employeeLeaveDto.getTotalDays());
				leaveDetails.setLeaveType("LOP");
			}
		} else if (employeeLeaveDto.getLeaveType().equalsIgnoreCase("sick")) {
			// sick leave
			if (sickLeaveAvailable != 0) {
				double sickLeave = sickLeaveAvailable - employeeLeaveDto.getTotalDays();
				employeeLeave.setSickLeaveAvailable(sickLeave);
				employeeLeave.setSickLeaveAvailed(sickLeaveAvailed + employeeLeaveDto.getTotalDays());
				employeeLeave.setTotalPaidLeavesAvailable(totalPaidLeavesAvailable - employeeLeaveDto.getTotalDays());
				employeeLeave.setTotalPaidLeavesAvailed(totalPaidLeavesAvailed + employeeLeaveDto.getTotalDays());
				leaveDetails.setLeaveType(employeeLeaveDto.getLeaveType());
			} else {
				employeeLeave.setLOP(employeeLeave.getLOP() + employeeLeaveDto.getTotalDays());
				leaveDetails.setLeaveType("LOP");
			}
		}
		employeeLeavesRepository.save(employeeLeave);

		leaveDetails.setEmployeeDetailsId(employeeLeaveDto.getEmployeeDetailsId());
		leaveDetails.setFromDate(employeeLeaveDto.getFromDate());
		leaveDetails.setToDate(employeeLeaveDto.getToDate());
		leaveDetails.setTotalDays(employeeLeaveDto.getTotalDays());
		leaveDetails.setLeaveSubject(employeeLeaveDto.getLeaveSubject());
		leaveDetailsRepository.save(leaveDetails);
		return "Data Captured!";
	}

	@Override
	public List<Map<String, Object>> getEmployeeLeaveHistoryById(Long id) {
		List<LeaveDetails> employeeLeaveList = leaveDetailsRepository.findAllByEmployeeDetailsId(id);
		List<Map<String, Object>> list = new ArrayList<>();
		for (LeaveDetails leaveDetails : employeeLeaveList) {
			Map<String, Object> map = new HashMap<>();
			map.put("leaveType", leaveDetails.getLeaveType());
			map.put("leaveSub", leaveDetails.getLeaveSubject());
			map.put("from", leaveDetails.getFromDate().toString().substring(0, 10));
			map.put("to", leaveDetails.getToDate().toString().substring(0, 10));
			map.put("totalDays", leaveDetails.getTotalDays());
			list.add(map);
		}
		return list;
	}

	@Scheduled(cron = "@monthly")
	public void addLeavesMonthly() {
		log.info("monthly leave add start!!");
		List<EmployeeLeaves> findAll = employeeLeavesRepository.findAll();
		for (EmployeeLeaves empLeaves : findAll) {
			double casualLeaveAvailable = empLeaves.getCasualLeaveAvailable() + 1;
			double sickLeaveAvailable = empLeaves.getSickLeaveAvailable() + 0.5;
			empLeaves.setCasualLeaveAvailable(casualLeaveAvailable);
			empLeaves.setSickLeaveAvailable(sickLeaveAvailable);
			empLeaves.setTotalPaidLeavesAvailable(sickLeaveAvailable + casualLeaveAvailable);
			employeeLeavesRepository.save(empLeaves);
		}
		log.info("monthly leave add end!!");
	}

}
