package com.smartgigInternal.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.smartgigInternal.entity.EmployeeDetails;
import com.smartgigInternal.repository.EmployeeDetailsRepository;

@RestController
@RequestMapping("/smg/download")
public class DownloadController {

	@Autowired
	private  EmployeeDetailsRepository employeeDetailsRepository;
	
	@GetMapping("/employee-csv") 
	public void employeeCSV(HttpServletResponse response) throws IOException, ParseException {
		response.setContentType("text/csv");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + "Smartgig_Employee" + ".csv";
		response.setHeader(headerKey, headerValue);
		List<EmployeeDetails> list = this.employeeDetailsRepository.findByStatus(true);
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader = { "Employee Id", "Name","Gender", "Email", "Contact No.","Employee Type","Designation","Role","Primary Location","Current Location","Qualification","Primary Skills","Secondry Skills","Date Of Birth","Date Of Join","CTC","Status"}; /// its show on header
		String[] nameMapping = { "empId", "fullName", "gender","email","mobileNo","empType","degination","role","primaryLocation","currentLocation","qualification","primarySkills","secondrySkills","dateOfBirth","dateOfJoining","ctc","status"}; // real attribute
		csvWriter.writeHeader(csvHeader);
		for (EmployeeDetails report : list) {
		    	csvWriter.write(report, nameMapping);
		}
		csvWriter.close();
	}

}
	
