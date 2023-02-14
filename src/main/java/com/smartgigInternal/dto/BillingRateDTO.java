package com.smartgigInternal.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BillingRateDTO {

	private String fullName;
	private String clientName;
	private String projectName;
	private Date fromDate;
	private Date toDate;
	private String rateType;
	private Long rate;
}
