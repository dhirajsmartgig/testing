package com.smartgigInternal.dto;

import java.util.Date;
import lombok.Data;

@Data
public class EmployeeBillingDTO {

	private String fullName;
	private String clientName;
	private String projectName;
	private Long day;
	private Long noOfLeaves;
	private Date fromDate;
	private Date toDate;
	private Long totalDays;
	private Long dayRate;
	private Long totalAmount;
}
