package com.smartgigInternal.dto;


import java.util.Date;

import lombok.Data;

@Data
public class EditEmployeeBillingDto {
	
	private String fullName;
	private String clientName;
	private String projectName;
	private Date fromDate;
	private Date toDate;
	private Long day;
	private Long noOfLeaves;
	private Long totalDays;
	private Long dayRate;
	private Long totalAmount;
}