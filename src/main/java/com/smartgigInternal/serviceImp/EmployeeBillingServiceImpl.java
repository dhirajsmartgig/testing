package com.smartgigInternal.serviceImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.smartgigInternal.dto.BillingRateDTO;
import com.smartgigInternal.dto.EditBillingRateDto;
import com.smartgigInternal.dto.EditEmployeeBillingDto;
import com.smartgigInternal.dto.EmployeeBillingDTO;
import com.smartgigInternal.dto.EmployeeBillingRequest;
import com.smartgigInternal.entity.BillingRate;
import com.smartgigInternal.entity.BillingRateRepository;
import com.smartgigInternal.entity.ClientDetails;
import com.smartgigInternal.entity.EmployeeBilling;
import com.smartgigInternal.entity.EmployeeDetails;
import com.smartgigInternal.entity.ProjectDetails;
import com.smartgigInternal.entity.ProjectEmployee;
import com.smartgigInternal.repository.ClinetDetailsRepository;
import com.smartgigInternal.repository.EmployeeDetailsRepository;
import com.smartgigInternal.repository.EmployeeProjectRepository;
import com.smartgigInternal.repository.EmployeerBillingRepository;
import com.smartgigInternal.repository.ProjectDetailsRepository;
import com.smartgigInternal.service.EmployeeBillingService;

@Service
public class EmployeeBillingServiceImpl implements EmployeeBillingService {

	@Autowired
	private EmployeerBillingRepository employeerBillingRepo;
	@Autowired
	private ClinetDetailsRepository clinetDetailsRepository;
	@Autowired
	private ProjectDetailsRepository projectDetailsRepository;
	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;
	@Autowired
	private EmployeeProjectRepository employeeProjectRepository;

	@Autowired
	private BillingRateRepository billingRateRepository;

	@Override
	public Map<String, String> saveEmployeeBill(EmployeeBillingDTO employeeBillingDTO) {
		Map<String, String> map = new HashMap<>();
		EmployeeBilling employeeBilling = new EmployeeBilling();
		EmployeeDetails employeeDetails = employeeDetailsRepository
				.findByFullNameAndStatus(employeeBillingDTO.getFullName(), true);
		ClientDetails clientDetails = clinetDetailsRepository
				.findByClientNameAndStatus(employeeBillingDTO.getClientName(), true);
		ProjectDetails projectDetails = projectDetailsRepository.findByProjectName(employeeBillingDTO.getProjectName());
		employeeBilling.setClientId(clientDetails.getClientId());
		employeeBilling.setProjectId(projectDetails.getProjectId());
		employeeBilling.setEmployeeDetailsId(employeeDetails.getEmployeeDetailsId());
		employeeBilling.setToDate(employeeBillingDTO.getToDate());
		employeeBilling.setFromDate(employeeBillingDTO.getFromDate());
		employeeBilling.setDay(employeeBillingDTO.getDay());
		employeeBilling.setDayRate(employeeBillingDTO.getDayRate());
		employeeBilling.setNoOfLeaves(employeeBillingDTO.getNoOfLeaves());
		employeeBilling.setTotalDays(employeeBillingDTO.getTotalDays());
		employeeBilling.setTotalAmount(employeeBillingDTO.getTotalAmount());
		EmployeeBilling bill = employeerBillingRepo.findByEmployeeDetailsIdAndClientIdAndProjectIdAndMonth(
				employeeBilling.getEmployeeDetailsId(), employeeBilling.getClientId(), employeeBilling.getProjectId(),
				employeeBilling.getToDate().getMonth());
		if (bill == null) {
			employeerBillingRepo.save(employeeBilling);
			map.put("msg", "Bill added");
		} else {
			map.put("msg", "bill already generated");
		}
		return map;
	}

	@Override
	public Map<String, Object> getEmployeeBillById(Long id) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		EmployeeBilling employeeBill = employeerBillingRepo.findById(id).get();
		Map<String, Object> map = new HashMap();
		ClientDetails clientDetails = clinetDetailsRepository.findByClientId(employeeBill.getClientId());
		EmployeeDetails employeeDetails = employeeDetailsRepository
				.findByEmployeeDetailsId(employeeBill.getEmployeeDetailsId());
		ProjectDetails projectDetails = projectDetailsRepository.findByProjectId(employeeBill.getProjectId());
		map.put("Day", employeeBill.getClientId());
		map.put("NoOfLeaves", employeeBill.getNoOfLeaves());
		map.put("DayRate", employeeBill.getDayRate());
		map.put("Days", employeeBill.getDay());
		map.put("FromDate", employeeBill.getFromDate().toString());
		map.put("ToDate", employeeBill.getToDate().toString());
		map.put("TotalDays", employeeBill.getTotalDays());
		map.put("TotalAmount", employeeBill.getTotalAmount());
		map.put("clientName", clientDetails.getClientName());
		map.put("projectName", projectDetails.getProjectName());
		map.put("EmployeeName", employeeDetails.getFullName());
		return map;
	}

	@Override
	public List<Map<String, Object>> getAllEmployeeBilling() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> responseList = new ArrayList();
		List<EmployeeBilling> employeeBilling = employeerBillingRepo.findAll();
		for (EmployeeBilling employeeBill : employeeBilling) {
			ClientDetails clientDetails = clinetDetailsRepository.findByClientId(employeeBill.getClientId());
			EmployeeDetails employeeDetails = employeeDetailsRepository
					.findByEmployeeDetailsId(employeeBill.getEmployeeDetailsId());
			ProjectDetails projectDetails = projectDetailsRepository.findByProjectId(employeeBill.getProjectId());
			Map<String, Object> map = new HashMap();
			map.put("billId", employeeBill.getId());
			map.put("NoOfLeaves", employeeBill.getNoOfLeaves());
			map.put("DayRate", employeeBill.getDayRate());
			map.put("Days", employeeBill.getDay());
			map.put("FromDate", employeeBill.getFromDate().toString());
			map.put("ToDate", employeeBill.getToDate().toString());
			map.put("TotalDays", employeeBill.getTotalDays());
			map.put("TotalAmount", employeeBill.getTotalAmount());
			map.put("clientName", clientDetails.getClientName());
			map.put("projectName", projectDetails.getProjectName());
			map.put("EmployeeName", employeeDetails.getFullName());
			responseList.add(map);
		}
		return responseList;
	}

	@Override
	public Map<String, Object> editEmployeeBill(EditEmployeeBillingDto editEmployeeBillingDto, Long id) {
		EmployeeBilling existemployeeBilling = employeerBillingRepo.getById(id);
		ClientDetails clientDetails = clinetDetailsRepository
				.findByClientNameAndStatus(editEmployeeBillingDto.getClientName(), true);
		ProjectDetails projectDetails = projectDetailsRepository
				.findByProjectName(editEmployeeBillingDto.getProjectName());
		EmployeeDetails employeeDetails = employeeDetailsRepository
				.findByFullNameAndStatus(editEmployeeBillingDto.getFullName(), true);
		Map<String, Object> map = new HashMap<>();
		if (existemployeeBilling != null) {
			if (editEmployeeBillingDto.getClientName() != null)
				existemployeeBilling.setClientId(clientDetails.getClientId());
			if (editEmployeeBillingDto.getFullName() != null)
				existemployeeBilling.setEmployeeDetailsId(employeeDetails.getEmployeeDetailsId());
			if (editEmployeeBillingDto.getProjectName() != null)
				existemployeeBilling.setProjectId(projectDetails.getProjectId());
			if (editEmployeeBillingDto.getDay() != null)
				existemployeeBilling.setDay(editEmployeeBillingDto.getDay());
			if (editEmployeeBillingDto.getDayRate() != null)
				existemployeeBilling.setDayRate(editEmployeeBillingDto.getDayRate());
			if (editEmployeeBillingDto.getFromDate() != null)
				existemployeeBilling.setFromDate(editEmployeeBillingDto.getFromDate());
			if (editEmployeeBillingDto.getToDate() != null)
				existemployeeBilling.setToDate(editEmployeeBillingDto.getToDate());
			if (editEmployeeBillingDto.getNoOfLeaves() != null)
				existemployeeBilling.setNoOfLeaves(editEmployeeBillingDto.getNoOfLeaves());
			if (editEmployeeBillingDto.getTotalDays() != null)
				existemployeeBilling.setTotalDays(editEmployeeBillingDto.getTotalDays());
			if (editEmployeeBillingDto.getTotalAmount() != null)
				existemployeeBilling.setTotalAmount(editEmployeeBillingDto.getTotalAmount());
			employeerBillingRepo.save(existemployeeBilling);
			map.put("msg", "Bill Updated");
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getProitAndLossReport(List<String> empName, Integer year) {
		List<Map<String, Object>> responseList = new ArrayList();
		if (empName.isEmpty()) {
			empName = employeeDetailsRepository.getAllEmployee();
			System.out.println(empName);
		}
		for (String name : empName) {
			Map<String, Object> map = new HashMap();
			EmployeeDetails employeeDetails = employeeDetailsRepository.findByFullNameAndStatus(name, true);
			if (employeeDetails != null) {
				List<EmployeeBilling> employeeBill = employeerBillingRepo
						.findByEmployeeDetailsId(employeeDetails.getEmployeeDetailsId(), year);
				double totalAmount = employeerBillingRepo.findBysum(employeeDetails.getEmployeeDetailsId(), year);
				map.put("assofor", totalAmount);
				map.put("empName", employeeDetails.getFullName());
				map.put("ctc", String.format("%.2f", employeeDetails.getCtc()));
				Date date = employeeDetails.getDateOfJoining();
				double salary = employeeDetails.getCtc() / 12;
				double perDay = salary / 30;
				int month = 11 - date.getMonth();
				int day = 31 - date.getDate();
				double currentYearCtc = (month * salary) + (day * perDay);
				Double projectCtc = calculateCtc(employeeBill);
				map.put("currentYearCtc", String.format("%.2f", currentYearCtc));
				map.put("projectCtc", String.format("%.2f", projectCtc));
				map.put("profilAndLoss", String.format("%.2f", projectCtc - currentYearCtc));
				map.put("empId", employeeDetails.getEmpId());
				responseList.add(map);
			}
		}
		return responseList;
	}

	private Double calculateCtc(List<EmployeeBilling> employeeBill) {
		Double ctc = 0D;
		for (EmployeeBilling b : employeeBill) {
			ctc += b.getTotalAmount();
		}
		return ctc;
	}

	@Override
	public List<Map<String, Object>> getClientAmountPie(List<String> clientName, String fromDate, String toDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> list = new ArrayList();
		Date dateAfter = sdf.parse(fromDate);
		Date dateBefore = sdf.parse(toDate);

		for (String cName : clientName) {
			Map<String, Object> map = new HashMap();
			ClientDetails client = clinetDetailsRepository.findByClientNameAndStatus(cName, true);
			Long amount = this.employeerBillingRepo.getTotalAmountByClientIdAndDateBetween(client.getClientId(),
					dateAfter, dateBefore);
			Long emp = this.employeerBillingRepo.getTotalEmpByClientIdAndDateBetween(client.getClientId(), dateAfter,
					dateBefore);
			map.put("clientName", cName);
			map.put("amount", amount);
			map.put("emp", emp);
			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getBillTable(List<String> clientName, String fromDate, String toDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> list = new ArrayList();
		Date dateAfter = sdf.parse(fromDate);
		Date dateBefore = sdf.parse(toDate);
		for (String cName : clientName) {
			Map<String, Object> map = new HashMap();
			ClientDetails client = clinetDetailsRepository.findByClientNameAndStatus(cName, true);
			Set<Long> projectId = this.employeerBillingRepo.finByClientId(client.getClientId());
			for (Long id : projectId) {
				ProjectDetails project = this.projectDetailsRepository.findByProjectId(id);
				Long amount = this.employeerBillingRepo.getTotalAmountByClientIdAndProjectIdAndDateBetween(
						client.getClientId(), id, dateAfter, dateBefore);
				Long emp = this.employeerBillingRepo.getTotalEmpByClientIdAndProjectIdAndDateBetween(
						client.getClientId(), id, dateAfter, dateBefore);
				map.put("clientName", cName);
				map.put("projectName", project.getProjectName());
				map.put("amount", amount);
				map.put("emp", emp);
				map.put("fromDate", dateAfter);
				map.put("toDate", dateBefore);
				list.add(map);
			}
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getEmpProject(String fullname) {
		Set<String> client = new HashSet();
		List<Map<String, Object>> ls = new ArrayList();
		List<Map<String, String>> list = new ArrayList();
		EmployeeDetails emp = employeeDetailsRepository.findByFullNameAndStatus(fullname, true);
		List<ProjectEmployee> empProjectList = this.employeeProjectRepository
				.findByEmployeeDetailsIdAndStatus(emp.getEmployeeDetailsId(), true);
		for (ProjectEmployee pemp : empProjectList) {
			ProjectDetails projectDetails = this.projectDetailsRepository.findByProjectId(pemp.getProjectId());
			ClientDetails clientD = this.clinetDetailsRepository.findByClientId(pemp.getClientId());
			Map<String, String> map = new HashMap();
			map.put("client", clientD.getClientName());
			map.put("project", projectDetails.getProjectName());
			list.add(map);
		}
		list.forEach(u -> {
			client.add(u.get("client"));
		});

		for (String cl : client) {
			Map<String, Object> mm = new HashMap();
			Set<String> project = new HashSet();
			for (Map<String, String> m : list) {
				if (cl.equals(m.get("client"))) {
					project.add(m.get("project"));
				}
			}
			mm.put("client", cl);
			mm.put("project", project);
			ls.add(mm);
		}
		return ls;
	}

	@Override
	public List<Map<String, Object>> getEmployeeBillingFilter(EmployeeBillingRequest carSpecs) {
		List<Map<String, Object>> responseList = new ArrayList();
		Map<String, Object> map = null;
		EmployeeBilling empBilling = new EmployeeBilling();

		if (carSpecs.getClientName() != null && !carSpecs.getClientName().equals("")) {
			ClientDetails client = clinetDetailsRepository.findByClientNameAndStatus(carSpecs.getClientName(), true);
			Long clientId = client.getClientId();

			empBilling.setClientId(clientId);
		}
		if (carSpecs.getProjectName() != null && !carSpecs.getProjectName().equals("")) {
			ProjectDetails project = projectDetailsRepository.findByProjectName(carSpecs.getProjectName());
			Long projectId = project.getProjectId();
			empBilling.setProjectId(projectId);
		}
		if (carSpecs.getFullName() != null && !carSpecs.getFullName().equals("")) {
			EmployeeDetails emp = employeeDetailsRepository.findByFullName(carSpecs.getFullName());
			Long employeeDetailsId = emp.getEmployeeDetailsId();
			empBilling.setEmployeeDetailsId(employeeDetailsId);
		}
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("fromDate").withIgnorePaths("toDate");
		List<EmployeeBilling> sortedList = employeerBillingRepo.findAll(Example.of(empBilling, matcher));
		EmployeeDetails employeeDetails = null;
		ProjectDetails projectDetails = null;
		ClientDetails clientDetails = null;
		for (EmployeeBilling employeeBill : sortedList) {
			employeeDetails = employeeDetailsRepository.findById(employeeBill.getEmployeeDetailsId()).get();
			projectDetails = projectDetailsRepository.findById(employeeBill.getProjectId()).get();
			clientDetails = clinetDetailsRepository.findById(employeeBill.getClientId()).get();
			map = new HashMap<>();
			map.put("billId", employeeBill.getId());
			map.put("NoOfLeaves", employeeBill.getNoOfLeaves());
			map.put("DayRate", employeeBill.getDayRate());
			map.put("Days", employeeBill.getDay());
			map.put("FromDate", employeeBill.getFromDate().toString());
			map.put("ToDate", employeeBill.getToDate().toString());
			map.put("TotalDays", employeeBill.getTotalDays());
			map.put("TotalAmount", employeeBill.getTotalAmount());
			map.put("clientName", clientDetails.getClientName());
			map.put("projectName", projectDetails.getProjectName());
			map.put("EmployeeName", employeeDetails.getFullName());
			responseList.add(map);
		}
		return responseList;

	}

	@Override
	public Map<String, Object> getProjectAssignedDate(String fullname, String clientName, String projectName) {
		Map<String, Object> map = new HashMap();
		EmployeeDetails emp = employeeDetailsRepository.findByFullNameAndStatus(fullname, true);
		ClientDetails clientDetails = clinetDetailsRepository.findByClientNameAndStatus(clientName, true);
		ProjectDetails projectDetails = projectDetailsRepository.findByProjectName(projectName);
		ProjectEmployee projectEmp = employeeProjectRepository.findByEmployeeDetailsIdAndClientIdAndProjectIdAndStatus(
				emp.getEmployeeDetailsId(), clientDetails.getClientId(), projectDetails.getProjectId(), true);
		if (projectEmp != null) {
			map.put("startdate", projectEmp.getOnBoardDate().toString());
			map.put("endDate", projectEmp.getOffBoardDate().toString());
		}
		return map;
	}

	@Override
	public Map<String, String> saveEmployeeBillingRate(BillingRateDTO billingRateDTO) {
		Map<String, String> map = new HashMap<>();
		BillingRate billingRate = new BillingRate();
		EmployeeDetails employeeDetails = employeeDetailsRepository
				.findByFullNameAndStatus(billingRateDTO.getFullName(), true);
		ClientDetails clientDetails = clinetDetailsRepository.findByClientNameAndStatus(billingRateDTO.getClientName(),
				true);
		ProjectDetails projectDetails = projectDetailsRepository.findByProjectName(billingRateDTO.getProjectName());
		billingRate.setClientId(clientDetails.getClientId());
		billingRate.setProjectId(projectDetails.getProjectId());
		billingRate.setEmployeeDetailsId(employeeDetails.getEmployeeDetailsId());
		billingRate.setToDate(billingRateDTO.getToDate());
		billingRate.setFromDate(billingRateDTO.getFromDate());
		billingRate.setRateType(billingRateDTO.getRateType());
		billingRate.setRate(billingRateDTO.getRate());
		BillingRate bill = billingRateRepository.findByEmployeeDetailsIdAndClientIdAndProjectIdAndMonth(
				billingRate.getEmployeeDetailsId(), billingRate.getClientId(), billingRate.getProjectId(),
				billingRate.getToDate().getMonth());
		System.out.println(bill);
		if (bill == null) {
			billingRateRepository.save(billingRate);
			map.put("msg", "Billing rate added");
		} else {
			map.put("msg", "Billing rate already present");
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getAllBillingRate() {
		List<Map<String, Object>> responseList = new ArrayList<>();
		List<BillingRate> billingRateList = billingRateRepository.findAll();

		for (BillingRate billingRate : billingRateList) {
			ClientDetails clientDetails = clinetDetailsRepository.findByClientId(billingRate.getClientId());
			EmployeeDetails employeeDetails = employeeDetailsRepository
					.findByEmployeeDetailsId(billingRate.getEmployeeDetailsId());
			ProjectDetails projectDetails = projectDetailsRepository.findByProjectId(billingRate.getProjectId());
			Map<String, Object> map = new HashMap<>();
			map.put("BillingRateId", billingRate.getId());
			map.put("FromDate", billingRate.getFromDate().toString());
			if(billingRate.getToDate()!=null) {
				map.put("ToDate", billingRate.getToDate().toString());
			}else {
				map.put("ToDate", null);
			}
			map.put("clientName", clientDetails.getClientName());
			map.put("projectName", projectDetails.getProjectName());
			map.put("EmployeeName", employeeDetails.getFullName());
			map.put("RateType", billingRate.getRateType());
			map.put("Rate", billingRate.getRate());
			responseList.add(map);
		}
		return responseList;
	}

	// edit billing

	@Override
	public Map<String, Object> editBillingRateById(EditBillingRateDto editBillingRateDto, Long billingRateId) {
		BillingRate existBillingRate = billingRateRepository.findById(billingRateId).get();
		ClientDetails clientDetails = clinetDetailsRepository
				.findByClientNameAndStatus(editBillingRateDto.getClientName(), true);
		ProjectDetails projectDetails = projectDetailsRepository.findByProjectName(editBillingRateDto.getProjectName());
		EmployeeDetails employeeDetails = employeeDetailsRepository
				.findByFullNameAndStatus(editBillingRateDto.getEmployeeName(), true);
		Map<String, Object> map = new HashMap<>();
		if (existBillingRate != null) {
			if (editBillingRateDto.getClientName() != null)
				existBillingRate.setClientId(clientDetails.getClientId());
			if (editBillingRateDto.getEmployeeName() != null)
				existBillingRate.setEmployeeDetailsId(employeeDetails.getEmployeeDetailsId());
			if (editBillingRateDto.getProjectName() != null)
				existBillingRate.setProjectId(projectDetails.getProjectId());
			if (editBillingRateDto.getFromDate() != null)
				existBillingRate.setFromDate(editBillingRateDto.getFromDate());
			if (editBillingRateDto.getToDate() != null)
				existBillingRate.setToDate(editBillingRateDto.getToDate());

			if (editBillingRateDto.getRateType() != null)
				existBillingRate.setRateType(editBillingRateDto.getRateType());
			if (editBillingRateDto.getRate() != null)
				existBillingRate.setRate(editBillingRateDto.getRate());

			billingRateRepository.save(existBillingRate);
			map.put("msg", "Billing Rate Updated");
		}
		return map;
	}

	@Override
	public Map<String, Object> getBillingRateById(Long billingRateId) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		BillingRate billingRate = billingRateRepository.findById(billingRateId).get();
		Map<String, Object> map = new HashMap<>();
		ClientDetails clientDetails = clinetDetailsRepository.findByClientId(billingRate.getClientId());
		EmployeeDetails employeeDetails = employeeDetailsRepository
				.findByEmployeeDetailsId(billingRate.getEmployeeDetailsId());
		ProjectDetails projectDetails = projectDetailsRepository.findByProjectId(billingRate.getProjectId());
//		map.put("Client", billingRate.getClientId());
		map.put("FromDate", billingRate.getFromDate().toString());
		map.put("ToDate", billingRate.getToDate().toString());
		map.put("clientName", clientDetails.getClientName());
		map.put("projectName", projectDetails.getProjectName());
		map.put("EmployeeName", employeeDetails.getFullName());
		map.put("RateType", billingRate.getRateType());
		map.put("Rate", billingRate.getRate());
		return map;
	}

	@Override
	public String deleteRateById(long billingRateId) {
		BillingRate billingRate = billingRateRepository.findById(billingRateId).get();
		billingRateRepository.delete(billingRate);
		return "deleted";
	}

	@Override
	public List<Map<String, Object>> getClientBill() {
		List<ClientDetails> clientList = clinetDetailsRepository.findByStatus(true);
		List<Map<String, Object>> list = new ArrayList();
		for (ClientDetails cName : clientList) {
			List<ProjectDetails> projectList = projectDetailsRepository.findByClientId(cName.getClientId());
			for (ProjectDetails project : projectList) {
				Map<String, Object> map = new HashMap();
				List<BillingRate> billingRate = billingRateRepository.findByProjectId(project.getProjectId());
				int total = 0;
				for (BillingRate empBilling : billingRate) {
					if (empBilling.getRateType().equals("Monthly")) {
						total += empBilling.getRate() * 1;
					}
					if (empBilling.getRateType().equals("Daily")) {
						total += empBilling.getRate() * 20;
					}
					if (empBilling.getRateType().equals("Hourly")) {
						total += empBilling.getRate() * 160;
					}
				}
				map.put("clientName", cName.getClientName());
				map.put("projectName", project.getProjectName());
				map.put("projectStatus", project.getStatus());
				map.put("projectStartSate", project.getStartDate().toString());
				if (project.getEndDate() != null) {
					map.put("projectEndDate", project.getEndDate().toString());
				} else {
					map.put("projectEndDate", null);
				}
				map.put("amount", total);
				map.put("emp", billingRate.size());
				list.add(map);

			}
		}
		return list;
	}

}
