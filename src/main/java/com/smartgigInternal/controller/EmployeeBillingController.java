package com.smartgigInternal.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartgigInternal.dto.BillingRateDTO;
import com.smartgigInternal.dto.EditBillingRateDto;
import com.smartgigInternal.dto.EditEmployeeBillingDto;
import com.smartgigInternal.dto.EmployeeBillingDTO;
import com.smartgigInternal.dto.EmployeeBillingRequest;
import com.smartgigInternal.service.EmployeeBillingService;

@RestController
@RequestMapping("/smg/billing")
public class EmployeeBillingController {

	@Autowired
	private EmployeeBillingService employeeBillingService;

	@PostMapping("/saveEmployeeBill")
	public ResponseEntity<?> saveEmployeeBill(@RequestBody EmployeeBillingDTO employeeBillingDTO) {
		return new ResponseEntity<>(employeeBillingService.saveEmployeeBill(employeeBillingDTO), HttpStatus.OK);
	}

	@GetMapping("/getEmployeeBill/{id}")
	public ResponseEntity<?> getEmployeeBillById(@PathVariable long id) {

		return new ResponseEntity<>(employeeBillingService.getEmployeeBillById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/getAllEmployeeBills")
	public ResponseEntity<?> getAllEmployeeBilling() {
		return new ResponseEntity<>(employeeBillingService.getAllEmployeeBilling(), HttpStatus.OK);
	}

	@PutMapping(value = "/editEmployeeBill/{id}")
	public ResponseEntity<?> editEmployeeBill(@RequestBody EditEmployeeBillingDto editEmployeeBillingDto,
			@PathVariable Long id) {
		return new ResponseEntity<>(employeeBillingService.editEmployeeBill(editEmployeeBillingDto, id), HttpStatus.OK);
	}

	@GetMapping(value = "/getPLReports")
	public ResponseEntity<?> getProitAndLossReport(@RequestParam List<String> empName, @RequestParam Integer year) {
		return new ResponseEntity<>(employeeBillingService.getProitAndLossReport(empName, year), HttpStatus.OK);
	}

	@GetMapping(value = "/getPieData")
	public ResponseEntity<?> getClientAmountPie(@RequestParam List<String> clientName, @RequestParam String fromDate,
			@RequestParam String toDate) throws ParseException {
		return new ResponseEntity<>(employeeBillingService.getClientAmountPie(clientName, fromDate, toDate),
				HttpStatus.OK);
	}

	@GetMapping(value = "/getBillTable")
	public ResponseEntity<?> getBillTable(@RequestParam List<String> clientName, @RequestParam String fromDate,
			@RequestParam String toDate) throws ParseException {
		return new ResponseEntity<>(employeeBillingService.getBillTable(clientName, fromDate, toDate), HttpStatus.OK);
	}

	@GetMapping("/getEmpProject/{fullname}")
	public ResponseEntity<?> getEmpProject(@PathVariable String fullname) {
		return new ResponseEntity<>(employeeBillingService.getEmpProject(fullname), HttpStatus.OK);
	}

	@GetMapping("/getProjectAssignedDate")
	public ResponseEntity<?> getProjectAssignedDate(@RequestParam String fullname, @RequestParam String clientName,
			@RequestParam String projectName) {
		return new ResponseEntity<>(employeeBillingService.getProjectAssignedDate(fullname, clientName, projectName),
				HttpStatus.OK);
	}

	@PostMapping("/getEmployeeBillingFilter")
	public ResponseEntity<?> getEmployeeBillingFilter(@RequestBody EmployeeBillingRequest carSpecs) {
		return new ResponseEntity<>(employeeBillingService.getEmployeeBillingFilter(carSpecs), HttpStatus.OK);
	}

	@PostMapping("/saveBillingRate")
	public ResponseEntity<?> saveBillingRate(@RequestBody BillingRateDTO billingRateDTO) {
		return new ResponseEntity<>(employeeBillingService.saveEmployeeBillingRate(billingRateDTO), HttpStatus.OK);
	}

	@GetMapping(value = "/getAllBillingRate")
	public ResponseEntity<?> getAllBillingRate() {
		return new ResponseEntity<>(employeeBillingService.getAllBillingRate(), HttpStatus.OK);
	}

//	editBillingRateById
	@PutMapping(value = "/editBillingRateById/{billingRateId}")
	public ResponseEntity<?> editBillingRateById(@RequestBody EditBillingRateDto editBillingRateDto,
			@PathVariable Long billingRateId) {
		return new ResponseEntity<>(employeeBillingService.editBillingRateById(editBillingRateDto, billingRateId),
				HttpStatus.OK);
	}

//	getBillingRateById
	@GetMapping("/getBillingRateById/{billingRateId}")
	public ResponseEntity<?> getBillingRateById(@PathVariable long billingRateId) {
		return new ResponseEntity<>(employeeBillingService.getBillingRateById(billingRateId), HttpStatus.OK);
	}
	
	
//	getBillingRateById
	@DeleteMapping("/deleteRateById/{billingRateId}")
	public ResponseEntity<?> deleteRateById(@PathVariable long billingRateId) {
		return new ResponseEntity<>(employeeBillingService.deleteRateById(billingRateId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getClientBill")
	public ResponseEntity<?> getClientBill(){
		return new ResponseEntity<>(employeeBillingService.getClientBill(), HttpStatus.OK);
	}

}
