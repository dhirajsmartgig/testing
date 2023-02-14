package com.smartgigInternal.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EditBillingRateDto {

//	private Long id;
	private String employeeName;
	private String clientName;
	private String projectName;
	private Date fromDate;
	private Date toDate;
	private String rateType;
	private Long rate;
}
