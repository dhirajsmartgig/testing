package com.smartgigInternal.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.smartgigInternal.dto.BillingRateDTO;
import com.smartgigInternal.dto.EditBillingRateDto;
import com.smartgigInternal.dto.EditEmployeeBillingDto;
import com.smartgigInternal.dto.EmployeeBillingDTO;
import com.smartgigInternal.dto.EmployeeBillingRequest;

public interface EmployeeBillingService {

	public Map<String, String> saveEmployeeBill(EmployeeBillingDTO employeeBillingDTO);

	public Map<String, Object> editEmployeeBill(EditEmployeeBillingDto editEmployeeBillingDto, Long id);

	public Map<String, Object> getEmployeeBillById(Long id);

	public List<Map<String, Object>> getAllEmployeeBilling();

	public List<Map<String, Object>> getProitAndLossReport(List<String> empName, Integer year);

	public List<Map<String, Object>> getClientAmountPie(List<String> clientName, String fromDate, String toDate)
			throws ParseException;

	public List<Map<String, Object>> getBillTable(List<String> clientName, String fromDate, String toDate)
			throws ParseException;

	public Map<String, Object> getProjectAssignedDate(String fullname, String clientName, String projectName);

	public List<Map<String, Object>> getEmpProject(String fullname);

	List<Map<String, Object>> getEmployeeBillingFilter(EmployeeBillingRequest carSpecs);

	public Map<String, String> saveEmployeeBillingRate(BillingRateDTO billingRateDTO);

	List<Map<String, Object>> getAllBillingRate();

	Map<String, Object> editBillingRateById(EditBillingRateDto editBillingRateDto, Long id);

	Map<String, Object> getBillingRateById(Long billingRateId);
	public String deleteRateById(long billingRateId);
	List<Map<String, Object>> getClientBill();
}