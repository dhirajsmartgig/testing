package com.smartgigInternal.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeeDTO {

	private String fullName;
	private String gender;
	private Date dateOfJoining;
	private String primaryLocation;
	private String currentLocation;
	private String qualification;
	private String primarySkills;
	private String secondrySkills;
	private Date dateOfBirth;
	private Date dateOfMarriage;
	private Date lastWorkingDay;
	private Double ctc;
	private String degination;
	private String empType;
	private String email;
	private String mobileNo;
	private String role;
	private Double exp;
	private Double fixedCtc;
	private Double variablePay;
	
	private String alternateMobile;
	private String hiredAs;
	private Long UAN;

}
