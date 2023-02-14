package com.smartgigInternal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class EmployeeDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeDetailsId;
	private String empId;
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
	private Double fixedCtc;
	private Double variablePay;
	private Double ctc;
	private String degination;
	private String empType;
	private boolean status;
	private Date lastWorkingDay;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String mobileNo;
	private String role;
	private String password;
	private String accessRole;
	private boolean changePassword;
	private Double exp;
	@Column(name = "uan")
	private Long UAN;
	@Column(name = "alternate_no")
	private String alternateMobile;
	@Column(name = "hired_as")
	private String hiredAs;

}
