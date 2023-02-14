package com.smartgigInternal.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeeLeaveDto {

	private String leaveSubject;
	private Date fromDate;
	private Date toDate;
	private double totalDays;
	private String leaveType;
	private Long employeeDetailsId;

}
